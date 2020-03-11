package com.wearetriple.tripleonboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP
import com.wearetriple.tripleonboarding.adapter.MapLevelAdapter
import com.wearetriple.tripleonboarding.model.DataCallback
import com.wearetriple.tripleonboarding.model.MapLevel
import com.wearetriple.tripleonboarding.repository.BaseEntityRepository
import kotlinx.android.synthetic.main.activity_map.*

const val DENSITY_MAP = 60F
const val DOUBLE_TAP_DESITY = 100

class MapActivity : AppCompatActivity() {

    private val mapLevels = arrayListOf<MapLevel>()
    private val repository = BaseEntityRepository<MapLevel>(MapLevel.DATABASE_KEY)
    private val mapLevelAdapter =
        MapLevelAdapter(mapLevels) { mapLevel -> mapLevelClicked(mapLevel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        initViews()
    }

    /**
     * Prepares the views inside this activity.
     */
    private fun initViews() {
        rvMapLevels.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
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
        val drawableResource: Int = when (mapLevel.level) {
            "1" -> R.drawable.level_1
            "2" -> R.drawable.level_2
            "3" -> R.drawable.level_3
            else -> R.drawable.level_0
        }

        ivMap.setImage(ImageSource.resource(drawableResource))
        ivMap.setMinimumScaleType(SCALE_TYPE_CENTER_CROP)
        ivMap.maxScale = DENSITY_MAP
        ivMap.setDoubleTapZoomDpi(DOUBLE_TAP_DESITY)
    }
}
