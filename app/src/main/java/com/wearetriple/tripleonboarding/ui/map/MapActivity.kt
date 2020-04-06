package com.wearetriple.tripleonboarding.ui.map

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.model.MapLevel
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    private val mapLevels = arrayListOf<MapLevel>()
    private val mapLevelAdapter =
        MapLevelAdapter(mapLevels) { mapLevel ->
            mapLevelClicked(
                mapLevel
            )
        }
    private lateinit var mapViewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        supportActionBar?.title = getString(R.string.title_map_screen)

        initViews()
        initViewModel()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvMapLevels.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        rvMapLevels.adapter = mapLevelAdapter
    }

    /**
     * Prepares the data needed for this activity.
     */
    private fun initViewModel() {
        mapViewModel = ViewModelProvider(this@MapActivity).get(MapViewModel::class.java)

        val liveData = mapViewModel.getAll()

        liveData.observe(this, Observer { list ->
            if (list != null) {
                mapLevels.clear()
                mapLevels.addAll(list)

                mapLevelClicked(list[0]) // Load up the first floor's image

                mapLevelAdapter.notifyDataSetChanged()
            }
        })
    }

    /**
     * Loads up the image connected to the level.
     */
    private fun mapLevelClicked(mapLevel: MapLevel) {
        Glide.with(this).asBitmap().load(mapLevel.image).into(object : CustomTarget<Bitmap?>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                ivMap.resetZoom()
                ivMap.setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }
}
