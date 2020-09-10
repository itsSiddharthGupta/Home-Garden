package com.example.homegarden.util

import android.content.Context
import android.util.DisplayMetrics




object UtilityMethods {
    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.resources
            .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}