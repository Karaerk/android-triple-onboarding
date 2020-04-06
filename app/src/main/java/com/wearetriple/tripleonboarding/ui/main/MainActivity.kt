package com.wearetriple.tripleonboarding.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.ui.department.overview.DepartmentOverviewActivity
import com.wearetriple.tripleonboarding.ui.fact.FactActivity
import com.wearetriple.tripleonboarding.ui.faq.FaqActivity
import com.wearetriple.tripleonboarding.ui.games.GamesActivity
import com.wearetriple.tripleonboarding.ui.hourbook.overview.HourBookOverviewActivity
import com.wearetriple.tripleonboarding.ui.info.InfoActivity
import com.wearetriple.tripleonboarding.ui.map.MapActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        btnInfo.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
        btnMap.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        btnGames.setOnClickListener {
            val intent = Intent(this, GamesActivity::class.java)
            startActivity(intent)
        }
        btnFacts.setOnClickListener {
            val intent = Intent(this, FactActivity::class.java)
            startActivity(intent)
        }
        btnFaq.setOnClickListener {
            val intent = Intent(this, FaqActivity::class.java)
            startActivity(intent)
        }
        btnDepartment.setOnClickListener {
            val intent = Intent(this, DepartmentOverviewActivity::class.java)
            startActivity(intent)
        }
        btnHours.setOnClickListener {
            val intent = Intent(this, HourBookOverviewActivity::class.java)
            startActivity(intent)
        }
    }
}