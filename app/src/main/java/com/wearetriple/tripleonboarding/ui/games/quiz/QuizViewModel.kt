package com.wearetriple.tripleonboarding.ui.games.quiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.database.EntityRepository
import com.wearetriple.tripleonboarding.model.Answer
import com.wearetriple.tripleonboarding.model.CORRECT_ANSWER
import com.wearetriple.tripleonboarding.model.GameStatus
import com.wearetriple.tripleonboarding.model.QuizQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application
    private val repository = EntityRepository()
    private val questionsLiveData = MediatorLiveData<List<QuizQuestion>>()
    var questions = questionsLiveData

    var gameStatus = MutableLiveData(GameStatus())
    private var leftoverQuestions = MutableLiveData<ArrayList<QuizQuestion>>(arrayListOf())
    var currentQuestion = MutableLiveData<QuizQuestion>()
    var message = MutableLiveData<String>()
    var gameOver = MutableLiveData(false)

    companion object {
        private const val DATABASE_KEY = "quiz"
        private const val MAX_POINTS_PER_QUESTION = 10
    }

    init {
        viewModelScope.launch {
            postLiveData()
        }
    }

    /**
     * Posts a new set of data inside the live data attribute.
     */
    private suspend fun postLiveData() = withContext(Dispatchers.IO) {
        val data = repository.getAllFromTable<QuizQuestion>(DATABASE_KEY)
        questionsLiveData.postValue(data)
    }

    /**
     * Prepares a new game by resetting data and getting the next question ready
     */
    /**
     * Prepares a new game by resetting data and getting the next question ready
     */
    fun prepareNewGame() {
        resetData()
        prepareNextQuestion()
    }

    /**
     * Resets all data to its initial state.
     */
    /**
     * Resets all data to its initial state.
     */
    private fun resetData() {
        gameStatus.value = GameStatus()
        leftoverQuestions.value!!.clear()
        leftoverQuestions.value!!.addAll(questionsLiveData.value!!)
        gameOver.value = false
    }

    /**
     * @return A String representation of the game's status.
     */
    /**
     * @return A String representation of the game's status.
     */
    fun getGameStatus(): String {
        return context.getString(
            R.string.label_game_status,
            getQuestionNumber(),
            questions.value!!.size
        )
    }

    /**
     * @return A String representation of the user's current score.
     */
    /**
     * @return A String representation of the user's current score.
     */
    fun getScore(): String {
        return context.getString(R.string.label_game_score, gameStatus.value!!.totalScore)
    }

    /**
     * @return A String representation of the game's end result.
     */
    /**
     * @return A String representation of the game's end result.
     */
    fun getEndResult(): String {
        return context.getString(
            R.string.description_quiz_done,
            context.resources.getQuantityString(
                R.plurals.number_of_points,
                gameStatus.value!!.totalScore,
                gameStatus.value!!.totalScore
            )
        )
    }

    /**
     * Prepares a new question for the user by randomly selecting one of the leftovers.
     */
    /**
     * Prepares a new question for the user by randomly selecting one of the leftovers.
     */
    private fun prepareNextQuestion() {
        if (leftoverQuestions.value!!.isEmpty()) {
            gameOver.value = true
        } else {
            val randomIndex = (0..leftoverQuestions.value!!.lastIndex).random()
            val nextQuestion = leftoverQuestions.value!![randomIndex]
            nextQuestion.answer.shuffle()

            currentQuestion.value = nextQuestion
        }

        gameStatus.value = GameStatus(totalScore = gameStatus.value!!.totalScore)
    }

    /**
     * Checks the result of the given answers and sends back a message to the user to let them
     * know the result.
     */
    /**
     * Checks the result of the given answers and sends back a message to the user to let them
     * know the result.
     */
    fun checkGivenAnswerResult(answer: Answer) {
        val messages = arrayListOf<String>()

        if (answer.correct == CORRECT_ANSWER) {
            messages.addAll(context.resources.getStringArray(R.array.correct_answer))

            updateGameStatusAfterAnswer(true)
        } else {
            messages.addAll(context.resources.getStringArray(R.array.incorrect_answer))
            updateGameStatusAfterAnswer(false)
        }

        val randomIndex = (0..messages.lastIndex).random()
        message.value = messages[randomIndex]
    }

    /**
     * Handles the things needed to be done when/before choosing the right answer.
     * For example: keeping track of wrong guesses, earned points, etc.
     */
    /**
     * Handles the things needed to be done when/before choosing the right answer.
     * For example: keeping track of wrong guesses, earned points, etc.
     */
    private fun updateGameStatusAfterAnswer(correctAnswer: Boolean) {
        if (correctAnswer) {
            val status = gameStatus.value!!
            status.currentCorrectAnswers++

            if (isAllCorrectAnswersFound()) {
                val earnedPoints = MAX_POINTS_PER_QUESTION.minus(status.currentWrongAnswers)
                status.totalScore += if (earnedPoints > 0) earnedPoints else 0
                leftoverQuestions.value!!.remove(currentQuestion.value)
                prepareNextQuestion()
            }
        } else {
            val status = gameStatus.value!!
            status.currentWrongAnswers++
        }
    }

    /**
     * @return The number of current question which indicates the user's progress.
     */
    /**
     * @return The number of current question which indicates the user's progress.
     */
    private fun getQuestionNumber(): Int {
        val numberOfQuestions = questions.value!!.size
        val difference = numberOfQuestions - leftoverQuestions.value!!.size

        return when (difference) {
            numberOfQuestions -> numberOfQuestions
            else -> difference + 1
        }
    }

    /**
     * Checks if all possible answers from a question is found.
     */
    /**
     * Checks if all possible answers from a question is found.
     */
    private fun isAllCorrectAnswersFound(): Boolean {
        val numberOfCorrectAnswers =
            currentQuestion.value!!.answer.filter { answer -> answer.correct == CORRECT_ANSWER }
                .size

        return (gameStatus.value!!.currentCorrectAnswers == numberOfCorrectAnswers)
    }
}