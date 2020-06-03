package com.wearetriple.tripleonboarding.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wearetriple.tripleonboarding.R
import kotlinx.android.synthetic.main.fragment_games.*

class GamesFragment : Fragment() {

    private lateinit var activityContext: AppCompatActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activityContext = (activity as AppCompatActivity)
        activityContext.supportActionBar?.show()

        initViews()
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
