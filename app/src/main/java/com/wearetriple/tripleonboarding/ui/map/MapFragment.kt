package com.wearetriple.tripleonboarding.ui.map

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.wearetriple.tripleonboarding.R
import com.wearetriple.tripleonboarding.extension.observeNonNull
import com.wearetriple.tripleonboarding.model.MapLevel
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment() {

    private lateinit var activityContext: AppCompatActivity
    private val mapLevelAdapter =
        MapLevelAdapter(arrayListOf()) { mapLevel ->
            mapLevelClicked(
                mapLevel
            )
        }
    private lateinit var mapViewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
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
        rvMapLevels.layoutManager =
            LinearLayoutManager(activityContext, RecyclerView.VERTICAL, true)
        rvMapLevels.adapter = mapLevelAdapter
    }

    /**
     * Prepares the data needed for this fragment.
     */
    private fun initViewModel() {
        mapViewModel = ViewModelProvider(activityContext).get(MapViewModel::class.java)

        mapViewModel.mapLevels.observeNonNull(viewLifecycleOwner, this::initRecyclerView)
    }

    /**
     * Initializes the recyclerview.
     */
    private fun initRecyclerView(list: List<MapLevel>) {
        pbActivity.visibility = View.GONE
        mapLevelClicked(list[0]) // Load up the first floor's image

        mapLevelAdapter.items.clear()
        mapLevelAdapter.items.addAll(list)
        mapLevelAdapter.notifyDataSetChanged()
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
