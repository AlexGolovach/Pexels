package com.renovavision.videos.screen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.renovavision.base.BaseFragment
import com.renovavision.base.extensions.gone
import com.renovavision.base.extensions.visible
import com.renovavision.videos.R
import com.renovavision.videos.adapter.VideosAdapter
import com.renovavision.videos.adapter.VideosLoadStateAdapter
import com.renovavision.videos.databinding.FragmentVideoListBinding
import com.renovavision.videos.di.featureModule
import com.renovavision.videos.viewModel.VideoListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoListFragment : BaseFragment(R.layout.fragment_video_list) {

    private val viewModel by viewModel<VideoListViewModel>()

    private val viewBinding by viewBinding<FragmentVideoListBinding>()

    override fun getFeatureModule() = featureModule

    private val videosAdapter = VideosAdapter {
        viewModel.navToVideoDetails(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initAdapter()
        initListener()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.videos.collectLatest {
                videosAdapter.submitData(it)
            }
        }
    }

    private fun initAdapter() = with(viewBinding) {
        rvVideos.adapter = videosAdapter.withLoadStateFooter(VideosLoadStateAdapter())

        videosAdapter.addLoadStateListener {
            if (it.append.endOfPaginationReached) {
                if (videosAdapter.itemCount < 1) {
                    evEmpty.hideProgress()
                    evEmpty.visible()
                    pbProgress.gone()
                    rvVideos.gone()
                } else {
                    pbProgress.gone()
                    rvVideos.visible()
                    evEmpty.gone()
                    evEmpty.hideProgress()
                }
            } else {
                if (videosAdapter.itemCount >= 1) {
                    rvVideos.visible()
                    pbProgress.gone()
                    evEmpty.gone()
                    evEmpty.hideProgress()
                }
            }
        }
    }

    private fun initListener() = with(viewBinding) {
        evEmpty.setRefreshListener {
            videosAdapter.refresh()
        }
    }
}