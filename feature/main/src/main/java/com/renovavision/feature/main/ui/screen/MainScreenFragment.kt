package com.renovavision.feature.main.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.renovavision.feature.main.R
import com.renovavision.feature.main.databinding.FragmentMainScreenBinding
import com.renovavision.feature.main.ui.adapter.TabAdapter
import com.renovavision.photos.screen.PhotoListFragment
import com.renovavision.videos.screen.VideoListFragment

class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {

    private val viewBinding by viewBinding<FragmentMainScreenBinding>()

    private var fragments = listOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragments = listOf(PhotoListFragment(), VideoListFragment())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(viewBinding) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = TabAdapter(this@MainScreenFragment, fragments)
        TabLayoutMediator(tlContainer, viewPager) { tab, position ->
            tab.text = listOf(
                getString(R.string.photos),
                getString(R.string.videos)
            )[position]
        }.attach()
    }
}