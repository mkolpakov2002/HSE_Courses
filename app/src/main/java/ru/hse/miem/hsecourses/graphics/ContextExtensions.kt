package ru.hse.miem.hsecourses.graphics

import android.content.Context


fun Context.dpToPx(dp: Int) = dp * this.resources.displayMetrics.density

fun Context.spToPx(sp: Int) = sp * this.resources.displayMetrics.scaledDensity