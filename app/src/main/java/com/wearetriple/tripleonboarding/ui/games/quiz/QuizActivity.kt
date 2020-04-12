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
import com.wearetriple.tripleonboarding.model.Answer
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

        val liveData = quizViewModel.getAll()
        liveData.observe(this, Observer { list ->
            if (list != null) {
                if (list.isEmpty())
                    return@Observer

                pbActivity.visibility = View.GONE
                startGame()
            }
        })

    }

    /**
     * (Re)starts the game by cleaning up last session.
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
        quizViewModel.gameStatus.observe(this, Observer {
            if (!quizViewModel.gameOver.value!!) {
                tvStatus.text = getString(
                    R.string.label_game_status,
                    quizViewModel.getQuestionNumber(),
                    quizViewModel.getAll().value!!.size
                )
            }

            tvScore.text = getString(R.string.label_game_score, it.totalScore)
        })

        quizViewModel.currentQuestion.observe(this, Observer {
            rvAnswers.adapter = AnswerAdapter(it.answer) { answer -> answerClicked(answer) }
            tvQuestion.text = it.question
        })

        quizViewModel.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        quizViewModel.gameOver.observe(this, Observer {
            if (it == true) {
                showGameOverDialog()
            }
        })
    }

    /**
     * Shows a dialog to the user with the options to leave the game or to restart the game.
     */
    private fun showGameOverDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(
            getString(
                R.string.description_quiz_done,
                resources.getQuantityString(R.plurals.number_of_points, quizViewModel.gameStatus.value!!.totalScore)
            )
        )
            .setCancelable(false)
            .setPositiveButton(getString(R.string.btn_leave_game)) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setNegativeButton(getString(R.string.btn_replay_game)) { dialog, _ ->
                dialog.cancel()
                startGame()
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
