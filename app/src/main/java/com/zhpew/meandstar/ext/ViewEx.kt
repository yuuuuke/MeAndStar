package com.zhpew.meandstar.utils

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import com.zhpew.meandstar.app.MyApplication
import kotlin.math.abs

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.noAnimClick(onPress:()->Unit): Modifier = composed {
    this.clickable(interactionSource= MutableInteractionSource(),null, onClick = {
        onPress()
    })
}


val Int.Go2Dp: Int
    get(){
        val scale: Float = MyApplication.instance.getResources().getDisplayMetrics().density
        return (this/scale+0.5f).toInt()
    }