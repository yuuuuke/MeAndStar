package com.zhpew.meandstar.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.DashPathEffect
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect.Companion.dashPathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import com.zhpew.meandstar.bean.Point
import com.zhpew.meandstar.utils.getScreenHeight
import com.zhpew.meandstar.utils.getScreenWidth
import java.util.Queue
import kotlin.math.round

object CustomView {

    private val animatorValue: MutableState<Float> = mutableStateOf(0f)
    val points = ArrayList<Point>()

    @Composable
    fun CustomWaveBgView(modifier: Modifier) {
        val animator = ObjectAnimator.ofFloat(this, "value", 0f, 200f)
        val paint = Paint()
        for (i in 0 until 5) {
            val point = Point()
            points.add(point)
        }
        paint.apply {
            strokeWidth = 5f
            style = PaintingStyle.Stroke
        }
        animator.apply {
            repeatCount = -1
            duration = 5000
            interpolator = LinearInterpolator()
            start()
        }
        Canvas(
            modifier
        ) {
            drawIntoCanvas {
                for (point in points) {
                    if (point.isStart) {
                        paint.color = Color(point.color)
                        paint.alpha = (200 - point.radus) / 200

                        it.drawCircle(
                            Offset(point.x.toFloat(), point.y.toFloat()),
                            point.radus,
                            paint
                        )
                    }
                }
                val path = Path()
                path.addOval(
                    Rect(
                        Offset(0f, 0f),
                        Offset(animatorValue.value, animatorValue.value)
                    )
                )
                paint.pathEffect = dashPathEffect(FloatArray(2) { 20f }, 5f)
                it.drawPath(path, paint)
            }
        }
    }

    private fun setValue(vararg: Float) {
        animatorValue.value = vararg
        for (point in points) {
            if (point.startProgress == vararg.toInt()) {
                point.startAnimator()
            }
        }
    }
}

