package com.wearetriple.tripleonboarding.ui.games

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wearetriple.tripleonboarding.R
import kotlinx.android.synthetic.main.fragment_games.*

class GamesFragment : Fragment(R.layout.fragment_games) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        requireActivity().actionBar?.show()
    }

    /**
     * Prepares the views inside this fragment.
     */
    private fun initViews() {
        cvQuiz.setOnClickListener { btnStartQuiz.callOnClick() }
        btnStartQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_gamesFragment_to_quizFragment)
        }

        cvMemory.setOnClickListener { btnStartMemory.callOnClick() }
        btnStartMemory.setOnClickListener {
            findNavController().navigate(R.id.action_gamesFragment_to_memoryFragment)
        }
    }
}
