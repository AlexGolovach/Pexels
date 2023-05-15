package com.renovavision.photos.screen

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.renovavision.base.BaseFragment
import com.renovavision.base.extensions.gone
import com.renovavision.base.extensions.visible
import com.renovavision.photos.R
import com.renovavision.photos.adapter.PhotosAdapter
import com.renovavision.photos.adapter.PhotosLoadStateAdapter
import com.renovavision.photos.databinding.FragmentPhotoListBinding
import com.renovavision.photos.di.featureModule
import com.renovavision.photos.dialog.ImageInfoDialogFragment
import com.renovavision.photos.viewModel.PhotoListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoListFragment : BaseFragment(R.layout.fragment_photo_list) {

    private val viewModel by viewModel<PhotoListViewModel>()

    private val viewBinding by viewBinding<FragmentPhotoListBinding>()

    override fun getFeatureModule() = featureModule

    private val photosAdapter = PhotosAdapter(
        { viewModel.navToPhotoDetails(it) },
        { ImageInfoDialogFragment.newInstance(it).show(childFragmentManager) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initAdapter()
        initListener()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.photos.collectLatest {
                photosAdapter.submitData(it)
            }
        }
    }

    private fun initAdapter() = with(viewBinding) {
        rvPhotos.adapter = photosAdapter.withLoadStateFooter(PhotosLoadStateAdapter())

        photosAdapter.addLoadStateListener {
            if (it.append.endOfPaginationReached) {
                if (photosAdapter.itemCount < 1) {
                    evEmpty.hideProgress()
                    evEmpty.visible()
                    pbProgress.gone()
                    rvPhotos.gone()
                } else {
                    pbProgress.gone()
                    rvPhotos.visible()
                    evEmpty.gone()
                    evEmpty.hideProgress()
                }
            } else {
                if (photosAdapter.itemCount >= 1) {
                    rvPhotos.visible()
                    pbProgress.gone()
                    evEmpty.gone()
                    evEmpty.hideProgress()
                }
            }
        }
    }

    private fun initListener() = with(viewBinding) {
        evEmpty.setRefreshListener {
            photosAdapter.refresh()
        }
    }
}