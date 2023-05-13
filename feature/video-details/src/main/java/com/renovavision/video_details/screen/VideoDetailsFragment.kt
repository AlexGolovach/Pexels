package com.renovavision.video_details.screen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.renovavision.base.BaseFragment
import com.renovavision.base.R.string
import com.renovavision.base.extensions.gone
import com.renovavision.base.extensions.makeLinks
import com.renovavision.base.extensions.navByUrl
import com.renovavision.domain.models.Video
import com.renovavision.video_details.R
import com.renovavision.video_details.databinding.FragmentVideoDetailsBinding
import com.renovavision.video_details.di.featureDetails
import com.renovavision.video_details.player.VideoPlayer
import com.renovavision.video_details.viewModel.VideoDetailsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class VideoDetailsFragment : BaseFragment(R.layout.fragment_video_details) {

    private val viewBinding by viewBinding<FragmentVideoDetailsBinding>()

    private val viewModel by viewModel<VideoDetailsViewModel>()

    private val args by navArgs<VideoDetailsFragmentArgs>()

    override fun getFeatureModule() = featureDetails

    private var video: Video? = null

    private val player by inject<VideoPlayer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        video = args.video
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initViews()
        initListeners()
    }

    private fun initObserver() {
        viewModel.shareVideo.observe(viewLifecycleOwner) {
            startActivity(it)
        }
    }

    private fun initViews() = with(viewBinding) {
        val userName = video?.user?.name
        val userUrl = video?.user?.url

        if (!userName.isNullOrEmpty())
            tvAuthor.text = getString(string.photographer, userName)
        else tvAuthor.gone()

        if (!userUrl.isNullOrEmpty()) {
            tvAuthorLink.text = getString(string.profile, userUrl)
            tvAuthorLink.makeLinks(userUrl to View.OnClickListener { navByUrl(userUrl) })
        } else tvAuthorLink.gone()
    }

    private fun initListeners() = with(viewBinding) {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.download -> {
                    video?.videoFile?.link?.let { link -> viewModel.loadVideo(link) }
                    false
                }
                R.id.share -> {
                    video?.videoFile?.link?.let { link -> viewModel.shareLink(link) }
                    false
                }
                else -> false
            }
        }
    }

    private fun initPlayer() = with(viewBinding) {
        video?.videoFile?.link?.let {
            playerView.player = player.initPlayer(it)
        }
    }

    override fun onStart() {
        super.onStart()
        initPlayer()
    }

    override fun onStop() {
        super.onStop()
        player.releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.releasePlayer()
    }
}