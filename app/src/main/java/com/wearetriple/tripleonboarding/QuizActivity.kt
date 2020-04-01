package com.wearetriple.tripleonboarding

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.adapter.QuizAnswerAdapter
import com.wearetriple.tripleonboarding.model.Answer
import com.wearetriple.tripleonboarding.model.CORRECT_ANSWER
import com.wearetriple.tripleonboarding.model.DataCallback
import com.wearetriple.tripleonboarding.model.QuizQuestion
import com.wearetriple.tripleonboarding.repository.BaseEntityRepository
import kotlinx.android.synthetic.main.activity_quiz.*

const val NO_CURRENT_QUESTION = -1
const val MAX_POINTS_PER_QUESTION = 10

class QuizActivity : AppCompatActivity() {

    private val questions = ArrayList<QuizQuestion>()
    private val answeredQuestions = ArrayList<QuizQuestion>()
    private val repository = BaseEntityRepository<QuizQuestion>(QuizQuestion.DATABASE_KEY)
    private var currentCorrectAnswers = 0
    private var currentWrongAnswers = 0
    private var totalScore = 0
    private var currentIndex = NO_CURRENT_QUESTION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        supportActionBar?.title = getString(R.string.title_quiz_screen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvAnswers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)

        getAllQuestions()
    }

    /**
     * Gets all questions stored in the database.
     */
    private fun getAllQuestions() {
        repository.getAll(object : DataCallback<QuizQuestion> {
            override fun onCallback(list: ArrayList<QuizQuestion>) {
                if (list.isEmpty())
                    return

                if (questions.isNotEmpty())
                    questions.clear()

                questions.addAll(list)

                startQuiz()
            }
        })
    }

    /**
     * Loads up a new question with its answers.
     */
    private fun updateState() {
        // Keep track of questions already asked to user
        if (currentIndex > NO_CURRENT_QUESTION) {
            val currentQuestion = questions[currentIndex]
            questions.remove(currentQuestion)
            answeredQuestions.add(currentQuestion)

            if (questions.isNullOrEmpty()) {
                showGameOverDialog()
                return
            }
        }

        val currentQuestion = prepareNextQuestion()
        currentQuestion.answer.shuffle()
        rvAnswers.adapter =
            QuizAnswerAdapter(currentQuestion.answer) { quizAnswer ->
                answerClicked(
                    quizAnswer
                )
            }

        currentCorrectAnswers = 0
        currentWrongAnswers = 0
        val numberOfQuestions = answeredQuestions.size + questions.size
        tvStatus.text =
            getString(R.string.label_game_status, answeredQuestions.size + 1, numberOfQuestions)
        tvScore.text = getString(R.string.label_game_score, totalScore)
        tvQuestion.text = currentQuestion.question
    }

    /**
     * Looks for the next question to ask to the user.
     */
    private fun prepareNextQuestion(): QuizQuestion {
        val randomIndex = (0..questions.lastIndex).random()
        currentIndex = randomIndex

        return questions[randomIndex]
    }

    /**
     * Gives the user the option to do the quiz again or to leave the quiz.
     */
    private fun showGameOverDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(getString(R.string.description_quiz_done, totalScore))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.btn_leave_game)) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setNegativeButton(getString(R.string.btn_replay_game)) { dialog, _ ->
                dialog.cancel()
                startQuiz()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.setTitle(getString(R.string.title_dialog_game))

        alert.show()
    }

    /**
     * (Re)starts the quiz by cleaning up last session.
     */
    private fun startQuiz() {
        // Clean up left overs from previous session
        if (answeredQuestions.isNotEmpty()) {
            questions.addAll(answeredQuestions)
            answeredQuestions.clear()
            currentIndex = NO_CURRENT_QUESTION
            currentWrongAnswers = 0
            totalScore = 0
        }
        updateState()
    }

    /**
     * Gives user feedback about the current state after clicking on an answer.
     */
    private fun answerClicked(answer: Answer) {
        val messages = arrayListOf<String>()

        when (answer.correct) {
            CORRECT_ANSWER -> {
                messages.addAll(resources.getStringArray(R.array.correct_answer))

                currentCorrectAnswers++
                val earnedPoints = MAX_POINTS_PER_QUESTION.minus(currentWrongAnswers)
                totalScore += if (earnedPoints > 0) earnedPoints else 0
                tvScore.text = getString(R.string.label_game_score, totalScore)

                checkAllCorrectAnswersFound()
            }
            else -> {
                currentWrongAnswers++
                messages.addAll(resources.getStringArray(R.array.incorrect_answer))
            }
        }

        val randomIndex = (0..messages.lastIndex).random()
        Toast.makeText(this, messages[randomIndex], Toast.LENGTH_SHORT).show()
    }

    /**
     * Provides the user a new question when the user has found all the correct answers of the
     * current question.
     */
    private fun checkAllCorrectAnswersFound() {
        val numberOfCorrectAnswers =
            questions[currentIndex].answer.filter { quizAnswer -> quizAnswer.correct == CORRECT_ANSWER }
                .size

        if (currentCorrectAnswers == numberOfCorrectAnswers)
            updateState()
    }
}
