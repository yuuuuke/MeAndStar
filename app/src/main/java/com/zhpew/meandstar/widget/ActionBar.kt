package com.zhpew.meandstar.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.zhpew.meandstar.R
import com.zhpew.meandstar.utils.noAnimClick

@Composable
fun ActionBar(title: String, onBackPress: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(
                colorResource(id = R.color.main_color)
            )
    ) {
        val (titleText, backImg) = createRefs()
        Text(text = title,
            modifier = Modifier.constrainAs(titleText) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        Image(
            painter = painterResource(id = R.drawable.icon_back),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 24.dp)
                .width(24.dp)
                .height(24.dp)
                .constrainAs(backImg){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .noAnimClick {
                    onBackPress()
                }
        )
    }
}