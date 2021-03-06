package com.wearetriple.tripleonboarding.ui.games.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Answer
import com.wearetriple.tripleonboarding.model.QuizQuestion
import com.wearetriple.tripleonboarding.ui.games.AnswerAdapter
import kotlinx.android.synthetic.main.fragment_quiz.*
import kotlinx.android.synthetic.main.item_game_dialog.view.*

class QuizFragment : Fragment(R.layout.fragment_quiz) {

    private lateinit var quizViewModel: QuizViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initViewModel()
        requireActivity().actionBar?.show()
    }

    /**
     * Prepares the views inside this fragment.
     */
    private fun initViews() {
        rvAnswers.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, true)
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        quizViewModel =
            ViewModelProvider(requireActivity()).get(QuizViewModel::class.java)

        quizViewModel.questions.observeNonNull(viewLifecycleOwner, this::initGame)
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
        quizViewModel.currentQuestion.observeNonNull(
            viewLifecycleOwner,
            this::updateCurrentQuestion
        )
        quizViewModel.message.observeNonNull(viewLifecycleOwner, this::showMessageToUser)
        quizViewModel.gameOver.observeNonNull(viewLifecycleOwner, this::observeGameOver)
        quizViewModel.userScore.observeNonNull(viewLifecycleOwner, this::observeUserScore)
        quizViewModel.userProgress.observeNonNull(viewLifecycleOwner, this::observeUserProgress)
    }

    /**
     * Lets the user know how many points have been scored.
     */
    private fun observeUserScore(message: String) {
        tvScore.text = message
    }

    /**
     * Lets the user know what the state of the current session is.
     */
    private fun observeUserProgress(message: String) {
        tvStatus.text = message
    }

    /**
     * Updates the current question shown on the screen.
     */
    private fun updateCurrentQuestion(question: QuizQuestion) {
        val answerAdapter = AnswerAdapter { answer -> answerClicked(answer) }
        answerAdapter.items = question.answer
        rvAnswers.adapter = answerAdapter
        tvQuestion.text = question.question
    }

    /**
     * Shows a new message to the user through a [Toast].
     */
    private fun showMessageToUser(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Shows the game over screen whenever the game is done.
     */
    private fun observeGameOver(boolean: Boolean) {
        if (boolean)
            showGameOverDialog()
    }

    /**
     * Shows a dialog to the user with the options to leave the game or to restart the game.
     */
    private fun showGameOverDialog() {
        val viewInflated = LayoutInflater.from(requireActivity())
            .inflate(R.layout.item_game_dialog, view as ViewGroup?, false)

        viewInflated.tvEndResult.text = quizViewModel.getEndResult()
        viewInflated.tvHighscore.text = quizViewModel.getHighscore()

        val dialogBuilder = AlertDialog.Builder(requireActivity())

        dialogBuilder.setView(viewInflated)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.btn_leave_game)) { dialog, _ ->
                dialog.dismiss()
                findNavController().navigateUp()
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
