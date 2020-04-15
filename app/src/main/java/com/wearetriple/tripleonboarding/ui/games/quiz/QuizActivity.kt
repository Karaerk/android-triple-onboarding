package com.wearetriple.tripleonboarding.ui.games.quiz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Answer
import com.wearetriple.tripleonboarding.model.QuizQuestion
import com.wearetriple.tripleonboarding.ui.games.AnswerAdapter
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    private lateinit var quizViewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        supportActionBar?.title = getString(R.string.title_quiz_screen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvAnswers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        quizViewModel =
            ViewModelProvider(this@QuizActivity).get(QuizViewModel::class.java)

        quizViewModel.questions.observeNonNull(this, this::initGame)
    }

    /**
     * Initializes the game.
     */
    private fun initGame(list: List<QuizQuestion>) {
        if (list.isEmpty())
            return

        pbActivity.visibility = View.GONE
        startGame()
    }

    /**
     * Starts the game by cleaning up last session.
     */
    private fun startGame() {
        // Clean up left overs from previous session
        quizViewModel.prepareNewGame()
        updateGameScreen()
    }

    /**
     * Makes sure the elements on the screen (like score, question progress, game over dialog) are
     * shown the right information at the right time.
     */
    private fun updateGameScreen() {
        quizViewModel.gameStatus.observeNonNull(this, this::updateGameStatus)
        quizViewModel.currentQuestion.observeNonNull(this, this::updateCurrentQuestion)
        quizViewModel.message.observeNonNull(this, this::showMessageToUser)
        quizViewModel.gameOver.observeNonNull(this, this::observeGameOver)
    }

    /**
     * Updates the game's status shown on the screen.
     */
    private fun updateGameStatus() {
        tvStatus.text = quizViewModel.getGameStatus()
        tvScore.text = quizViewModel.getScore()
    }

    /**
     * Updates the current question shown on the screen.
     */
    private fun updateCurrentQuestion(question: QuizQuestion) {
        rvAnswers.adapter = AnswerAdapter(question.answer) { answer -> answerClicked(answer) }
        tvQuestion.text = question.question
    }

    /**
     * Shows a new message to the user through a [Toast].
     */
    private fun showMessageToUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Shows the game over screen whenever the game is done.
     */
    private fun observeGameOver(boolean: Boolean) {
        if (boolean) {
            showGameOverDialog()
        }
    }

    /**
     * Shows a dialog to the user with the options to leave the game or to restart the game.
     */
    private fun showGameOverDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(
            quizViewModel.getEndResult()
        )
            .setCancelable(false)
            .setPositiveButton(getString(R.string.btn_leave_game)) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setNegativeButton(getString(R.string.btn_replay_game)) { dialog, _ ->
                dialog.cancel()
                quizViewModel.prepareNewGame()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.setTitle(getString(R.string.title_dialog_game))

        alert.show()
    }

    /**
     * Starts checking the result of the given answer.
     */
    private fun answerClicked(answer: Answer) {
        quizViewModel.checkGivenAnswerResult(answer)
    }

}
