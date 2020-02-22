package com.realitix.mealassistant.util

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

class FabAnimation {
    companion object {
        @JvmStatic
        fun rotate(v: View, rotate: Boolean): Boolean {
            v.animate().setDuration(200)
                .setListener(object : AnimatorListenerAdapter() {
                    // Must be set to avoid glitch
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                    }
                })
                .rotation(if (rotate) 135f else 0f)
            return rotate
        }

        @JvmStatic
        fun show(v: View) {
            v.visibility = View.VISIBLE
            v.alpha = 0f
            v.translationY = v.height.toFloat()
            v.animate()
                .setDuration(200)
                .translationY(0f)
                .setListener(object : AnimatorListenerAdapter() {
                    // Must be set to avoid glitch
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                    }
                })
                .alpha(1f)
                .start()
        }

        @JvmStatic
        fun hide(v: View) {
            v.visibility = View.VISIBLE
            v.alpha = 1f
            v.translationY = 0f
            v.animate()
                .setDuration(200)
                .translationY(v.height.toFloat())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        v.visibility = View.INVISIBLE
                        super.onAnimationEnd(animation)
                    }
                }).alpha(0f)
                .start()
        }
    }
}