package com.zhpew.meandstar.bean

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.animation.DecelerateInterpolator
import com.zhpew.meandstar.utils.getScreenHeight
import com.zhpew.meandstar.utils.getScreenWidth

class Point {

    private val animator = ObjectAnimator.ofFloat(this, "value", 0f, 200f)

    var x: Int
    var y: Int
    var startProgress: Int
    var isStart = false
    var radus = 0f
    var color:Int

    init {
        x = 220 + (Math.random() * (getScreenWidth() - 400)).toInt()
        y = 220 + (Math.random() * (getScreenHeight() - 400)).toInt()
        color = Color.rgb(
            (Math.random() * 255).toFloat(),
            (Math.random() * 255).toFloat(),
            (Math.random() * 255).toFloat()
        )
        startProgress = (Math.random() * 200).toInt()
        animator.duration = 3000
        animator.interpolator = DecelerateInterpolator()
        animator.addListener(object:AnimatorListener{
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                isStart = false
                startProgress = (Math.random() * 200).toInt()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })
    }

    private fun reset() {
        x = 220 + (Math.random() * (getScreenWidth() - 400)).toInt()
        y = 220 + (Math.random() * (getScreenHeight() - 400)).toInt()
        radus = 0f
    }

    fun startAnimator() {
        reset()
        isStart = true
        animator.start()
    }

    private fun setValue(vararg: Float) {
        radus = vararg/2
    }
}