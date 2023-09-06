package com.jhkim.core.common

import android.content.res.Resources

object Util {

    fun Float.fromDpToPx(): Int =
        (this * Resources.getSystem().displayMetrics.density).toInt()

}