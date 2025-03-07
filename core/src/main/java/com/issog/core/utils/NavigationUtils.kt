package com.issog.core.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

object NavigationUtils {
    fun NavController.safeNavigate(@IdRes destination: Int, bundle: Bundle? = null) {
        try {
            val navOption = NavOptions.Builder()
                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                .build()

            navigate(destination, bundle, navOption)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun NavController.safeNavigate(destination: NavDirections) {
        try {
            val navOption = NavOptions.Builder()
                .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                .build()

            navigate(destination, navOption)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}