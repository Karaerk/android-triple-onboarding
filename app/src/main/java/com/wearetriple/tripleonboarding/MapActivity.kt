package com.wearetriple.tripleonboarding

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.wearetriple.tripleonboarding.adapter.MapLevelAdapter
import com.wearetriple.tripleonboarding.model.DataCallback
import com.wearetriple.tripleonboarding.model.MapLevel
import com.wearetriple.tripleonboarding.repository.BaseEntityRepository
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    private val mapLevels = arrayListOf<MapLevel>()
    private val repository = BaseEntityRepository<MapLevel>(MapLevel.DATABASE_KEY)
    private val mapLevelAdapter =
        MapLevelAdapter(mapLevels) { mapLevel -> mapLevelClicked(mapLevel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        supportActionBar?.title = getString(R.string.title_map_screen)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvMapLevels.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        rvMapLevels.adapter = mapLevelAdapter

        repository.getAll(object : DataCallback<MapLevel> {
            override fun onCallback(list: ArrayList<MapLevel>) {
                if (mapLevels.isNotEmpty())
                    mapLevels.clear()

                mapLevels.addAll(list)

                mapLevelClicked(mapLevels[0]) // load up the first floor's image

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
                ivMap.setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }
}
