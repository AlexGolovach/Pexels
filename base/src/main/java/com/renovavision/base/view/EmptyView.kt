package com.renovavision.base.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.renovavision.base.databinding.LayoutEmptyViewBinding
import com.renovavision.base.extensions.gone
import com.renovavision.base.extensions.visible

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding<LayoutEmptyViewBinding>(CreateMethod.INFLATE)

    private var refreshListener: (() -> Unit)? = null

    init {
        binding.btnTryAgain.setOnClickListener {
            refreshListener?.invoke()
            showProgress()
        }
    }

    fun setRefreshListener(listener: () -> Unit) {
        refreshListener = listener
    }

    fun hideProgress() = with(binding) {
        btnTryAgain.visible()
        pbProgress.gone()
    }

    private fun showProgress() = with(binding) {
        pbProgress.visible()
        btnTryAgain.gone()
    }
}