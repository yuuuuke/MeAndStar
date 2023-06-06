package com.zhpew.meandstar.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.noAnimClick(onPress:()->Unit): Modifier = composed {
    this.clickable(interactionSource= MutableInteractionSource(),null, onClick = {
        onPress()
    })
}