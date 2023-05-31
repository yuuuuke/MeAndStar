package com.zhpew.meandstar.widget

import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import com.zhpew.meandstar.bean.Point

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
            strokeWidth = 3f
            style = PaintingStyle.Stroke
        }
        animator.apply {
            repeatCount = -1
            duration = 3000
            interpolator = LinearInterpolator()
            start()
        }
        Canvas(
            modifier
        ) {
            drawIntoCanvas {
//                paint.pathEffect = dashPathEffect(FloatArray(2) { 5f }, 5f)
                for (point in points) {
                    if (point.isStart) {
                        paint.color = Color(point.color)
                        paint.alpha = (100 - point.radus) / 100

                        it.drawCircle(
                            Offset(point.x.toFloat(), point.y.toFloat()),
                            point.radus,
                            paint
                        )
                    }
                }
                animatorValue.value
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

