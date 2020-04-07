package com.wearetriple.tripleonboarding.ui.games

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.ui.games.memory.MemoryActivity
import com.wearetriple.tripleonboarding.ui.games.quiz.QuizActivity
import kotlinx.android.synthetic.main.activity_games.*

class GamesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)
        supportActionBar?.title = getString(R.string.title_games_screen)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        cvQuiz.setOnClickListener { btnStartQuiz.callOnClick() }
        btnStartQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }

        cvMemory.setOnClickListener { btnStartMemory.callOnClick() }
        btnStartMemory.setOnClickListener {
            val intent = Intent(this, MemoryActivity::class.java)
            startActivity(intent)
        }
    }
}
