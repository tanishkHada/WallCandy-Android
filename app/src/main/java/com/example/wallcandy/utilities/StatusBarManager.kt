package com.example.wallcandy.utilities

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

object StatusBarManager {

    fun setStatusBarColorAndContentsColor(activity: Activity, color : Int){
        if (Build.VERSION.SDK_INT >= 21) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = activity.resources.getColor(color)

            // Set the status bar icons to be light on dark background and dark on light background
            val decorView = window.decorView
            if (isLightStatusBarIconNeeded(activity, color)) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = 0
            }
        }
    }

    private fun isLightStatusBarIconNeeded(activity: Activity, color : Int): Boolean {
        // Determine if the background color is light or dark
        // You may need to implement your own logic here based on your background color
        val backgroundColor = activity.getColor(color)
        val darkness = 1 - (0.299 * Color.red(backgroundColor) +
                0.587 * Color.green(backgroundColor) +
                0.114 * Color.blue(backgroundColor)) / 255
        return darkness < 0.5 // Adjust this threshold as needed
    }

}