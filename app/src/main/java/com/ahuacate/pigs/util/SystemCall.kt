package com.ahuacate.pigs.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class SystemCall(private val context : Context?) {

    private val TAP : Long = 50


    fun vibrate() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if(Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(TAP, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("deprecation")
            vibrator.vibrate(TAP)
        }
    }

}