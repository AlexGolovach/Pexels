package com.renovavision.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    protected abstract fun getFeatureModule(): Module

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(getFeatureModule())
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(getFeatureModule())
    }

    fun onBackPressed() {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}