package com.wearetriple.tripleonboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP
import kotlinx.android.synthetic.main.activity_map.*

const val DENSITY_MAP = 60F
const val DOUBLE_TAP_DESITY = 100

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        initViews()
    }

    private fun initViews() {
        ivMap.setImage(ImageSource.resource(R.drawable.level_1))
        ivMap.setMinimumScaleType(SCALE_TYPE_CENTER_CROP)
        ivMap.maxScale = DENSITY_MAP
        ivMap.setDoubleTapZoomDpi(DOUBLE_TAP_DESITY)
    }
}
