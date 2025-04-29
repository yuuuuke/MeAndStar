package com.zhpew.meandstar.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.zhpew.meandstar.R
import com.zhpew.meandstar.ext.noAnimClick

@Composable
fun CustomActionBar(
    title: String,
    rightImg: Painter? = null,
    onBackPress: () -> Unit,
    rightIconPress: (() -> Unit)? = null
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(24.dp)
            .background(colorResource(id = R.color.main_color))
    ) {}
    ConstraintLayout(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .background(
                colorResource(id = R.color.main_color)
            )
    ) {
        val (nextImg, titleText, backImg) = createRefs()
        Text(text = title,
            fontSize = 15.sp,
            modifier = Modifier.constrainAs(titleText) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        Image(
            painter = painterResource(id = R.drawable.icon_back),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 24.dp)
                .width(24.dp)
                .height(24.dp)
                .constrainAs(backImg) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .noAnimClick {
                    onBackPress()
                }
        )
        rightImg?.let {
            Image(
                painter = rightImg,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 24.dp)
                    .width(24.dp)
                    .height(24.dp)
                    .constrainAs(nextImg) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .noAnimClick {
                        rightIconPress?.invoke()
                    })
        }
    }
}