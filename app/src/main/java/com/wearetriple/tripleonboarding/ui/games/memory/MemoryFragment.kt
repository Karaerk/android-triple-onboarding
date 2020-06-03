package com.wearetriple.tripleonboarding.ui.games.memory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.Answer
import com.wearetriple.tripleonboarding.model.MemoryQuestion
import com.wearetriple.tripleonboarding.ui.games.AnswerAdapter
import kotlinx.android.synthetic.main.fragment_memory.*

class MemoryFragment : Fragment() {

    private lateinit var activityContext: AppCompatActivity
    private lateinit var memoryViewModel: MemoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memory, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activityContext = (activity as AppCompatActivity)
        activityContext.supportActionBar?.show()

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this fragment.
     */
    private fun initViews() {
        rvAnswers.layoutManager = LinearLayoutManager(activityContext, RecyclerView.VERTICAL, true)
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        memoryViewModel =
            ViewModelProvider(activityContext).get(MemoryViewModel::class.java)

        memoryViewModel.questions.observeNonNull(viewLifecycleOwner, this::initGame)
    }

    /**
     * Initializes the game.
     */
    private fun initGame(list: List<MemoryQuestion>) {
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
        memoryViewModel.prepareNewGame()
        updateGameScreen()
    }

    /**
     * Makes sure the elements on the screen (like score, question progress, game over dialog) are
     * shown the right information at the right time.
     */
    private fun updateGameScreen() {
        memoryViewModel.gameStatus.observeNonNull(viewLifecycleOwner, this::updateGameStatus)
        memoryViewModel.currentQuestion.observeNonNull(
            viewLifecycleOwner,
            this::updateCurrentQuestion
        )
        memoryViewModel.message.observeNonNull(viewLifecycleOwner, this::showMessageToUser)
        memoryViewModel.gameOver.observeNonNull(viewLifecycleOwner, this::observeGameOver)
    }

    /**
     * Updates the game's status shown on the screen.
     */
    private fun updateGameStatus() {
        tvStatus.text = memoryViewModel.getGameStatus()
        tvScore.text = memoryViewModel.getScore()
    }

    /**
     * Updates the current question shown on the screen.
     */
    private fun updateCurrentQuestion(question: MemoryQuestion) {
        rvAnswers.adapter = AnswerAdapter(question.answer) { answer -> answerClicked(answer) }
        Glide.with(this).load(question.image).into(ivQuestion)
    }

    /**
     * Shows a new message to the user through a [Toast].
     */
    private fun showMessageToUser(message: String) {
        Toast.makeText(activityContext, message, Toast.LENGTH_SHORT).show()
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
        val viewInflated = LayoutInflater.from(activityContext)
            .inflate(R.layout.item_game_dialog, view as ViewGroup?, false)

        viewInflated.findViewById<TextView>(R.id.tvEndResult).text = memoryViewModel.getEndResult()
        viewInflated.findViewById<TextView>(R.id.tvHighscore).text = memoryViewModel.getHighscore()

        val dialogBuilder = AlertDialog.Builder(activityContext)

        dialogBuilder.setView(viewInflated)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.btn_leave_game)) { dialog, _ ->
                dialog.dismiss()
                findNavController().navigateUp()
            }
            .setNegativeButton(getString(R.string.btn_replay_game)) { dialog, _ ->
                dialog.cancel()
                memoryViewModel.prepareNewGame()
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
        memoryViewModel.checkGivenAnswerResult(answer)
    }

}
