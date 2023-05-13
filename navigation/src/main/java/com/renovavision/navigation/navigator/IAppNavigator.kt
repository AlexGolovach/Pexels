package com.renovavision.navigation.navigator

import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment

abstract class IAppNavigator {

    protected var navController: NavController? = null

    fun bind(navHostFragment: NavHostFragment) {
        navController = (navHostFragment as NavHost).navController
    }
}