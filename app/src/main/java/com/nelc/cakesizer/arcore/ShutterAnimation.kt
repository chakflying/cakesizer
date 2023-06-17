package com.nelc.cakesizer.arcore

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation

class ShutterAnimation(private val shutterFlash: View) : Animation() {
    init {
        repeatCount = 0
        duration = 500L
        startOffset = 0
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        if (interpolatedTime < 0.3f) {
            shutterFlash.alpha = interpolatedTime / 0.3f
        } else {
            shutterFlash.alpha = (1f - interpolatedTime) / 0.7f
        }
    }
}
