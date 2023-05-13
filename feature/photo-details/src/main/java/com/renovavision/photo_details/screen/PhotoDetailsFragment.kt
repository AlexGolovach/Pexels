package com.renovavision.photo_details.screen

import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.renovavision.base.*
import com.renovavision.base.R.drawable
import com.renovavision.base.R.string
import com.renovavision.base.extensions.getProgressDialog
import com.renovavision.base.extensions.gone
import com.renovavision.base.extensions.makeLinks
import com.renovavision.base.extensions.navByUrl
import com.renovavision.domain.models.Photo
import com.renovavision.photo_details.R
import com.renovavision.photo_details.databinding.FragmentPhotoDetailsBinding
import com.renovavision.photo_details.di.featureModule
import com.renovavision.photo_details.viewModel.PhotoDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoDetailsFragment : BaseFragment(R.layout.fragment_photo_details) {

    private val viewModel by viewModel<PhotoDetailsViewModel>()

    private val viewBinding by viewBinding<FragmentPhotoDetailsBinding>()

    override fun getFeatureModule() = featureModule

    private val args by navArgs<PhotoDetailsFragmentArgs>()

    private var data: Photo? = null

    private val progressDialog by lazy { getProgressDialog() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = args.photo
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initViews()
        initListeners()
    }

    private fun initObserver() {
        viewModel.showProgress.observe(viewLifecycleOwner) {
            if (it)
                progressDialog.show()
            else progressDialog.dismiss()
        }

        viewModel.shareImage.observe(viewLifecycleOwner) {
            startActivity(it)
        }
    }

    private fun initViews() = with(viewBinding) {
        val description = data?.alt
        val photographer = data?.photographer
        val photographerUrl = data?.photographerUrl

        ivPoster.load(data?.imageUrl) {
            crossfade(true)
            allowHardware(false)
            placeholder(drawable.image_placeholder)
            fallback(drawable.image_placeholder)
            error(drawable.image_placeholder)
        }

        if (!description.isNullOrEmpty())
            tvImageDescription.text = getString(string.image_description, description)
        else tvImageDescription.gone()

        if (!photographer.isNullOrEmpty())
            tvPhotographer.text = getString(string.photographer, photographer)
        else tvPhotographer.gone()

        if (!photographerUrl.isNullOrEmpty()) {
            tvPhotographerLink.text = getString(string.profile, photographerUrl)
            tvPhotographerLink.makeLinks(photographerUrl to View.OnClickListener { navByUrl(photographerUrl) })
        } else tvPhotographerLink.gone()
    }

    private fun initListeners() = with(viewBinding) {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.download -> {
                    data?.originalImageUrl?.let { url -> viewModel.loadImage(url) }
                    false
                }
                R.id.share_as_link -> {
                    data?.originalImageUrl?.let { url -> viewModel.shareLink(url) }
                    false
                }
                R.id.share_file -> {
                    viewModel.shareFile(ivPoster.drawable.toBitmap())
                    false
                }
                else -> false
            }
        }
    }
}