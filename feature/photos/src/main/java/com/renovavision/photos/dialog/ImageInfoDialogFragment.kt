package com.renovavision.photos.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.renovavision.base.R.string
import com.renovavision.base.extensions.gone
import com.renovavision.base.extensions.makeLinks
import com.renovavision.base.extensions.navByUrl
import com.renovavision.domain.models.Photo
import com.renovavision.photos.R
import com.renovavision.photos.databinding.DialogFragmentImageInfoBinding

class ImageInfoDialogFragment : DialogFragment(R.layout.dialog_fragment_image_info) {

    private val binding by viewBinding<DialogFragmentImageInfoBinding>()

    private var data: Photo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            dialog?.window?.setLayout(
                Constraints.LayoutParams.MATCH_PARENT,
                Constraints.LayoutParams.WRAP_CONTENT
            )
            val back = ColorDrawable(Color.TRANSPARENT)
            val margin = 100
            val inset = InsetDrawable(back, margin)
            dialog?.window?.setBackgroundDrawable(inset)
        }
    }

    private fun initViews() = with(binding) {
        val description = data?.alt
        val photographer = data?.photographer
        val photographerUrl = data?.photographerUrl

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

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, ImageInfoDialogFragment::class.java.simpleName)
    }

    companion object {
        fun newInstance(photo: Photo) = ImageInfoDialogFragment().apply {
            data = photo
        }
    }
}