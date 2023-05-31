package com.zhpew.meandstar.fragment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhpew.meandstar.R
import com.zhpew.meandstar.base.BaseFragment
import com.zhpew.meandstar.vm.DiaryListViewModel

class DiaryFragment : BaseFragment<DiaryListViewModel>() {

    @Composable
    override fun InitComposeView() {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(colorResource(id = R.color.main_color))) {}
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(colorResource(id = R.color.main_color))
            ) {
                Text(text = "日记本", color = colorResource(id = R.color.text_color), fontSize = 18.sp)
            }
            ListData()
        }
    }

    @Composable
    private fun ListData() {
        LazyColumn(modifier = Modifier
            .background(colorResource(id = R.color.bg_color))
            .fillMaxWidth()
            .fillMaxHeight(), content = {
            items(10) {
                Item()
            }
        })
    }

    @Composable
    private fun Item() {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Column(verticalArrangement = Arrangement.Bottom, modifier = Modifier
                .padding(end = 16.dp)
                .height(100.dp)) {
                Text(text = "星期三", color = colorResource(id = R.color.text_color), fontSize = 12.sp)
                Text(text = "8月24日", color = colorResource(id = R.color.text_color), fontSize = 14.sp)
                Text(text = "15:00", color = colorResource(id = R.color.text_color), fontSize = 18.sp)
            }
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(id = R.color.main_color))
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "在数据持久上使用了Room数据库，使用DataStore存放一些不是很重要的数据，没有架设服务器。在使用Room的时候遇到了比较多的坑，并且网上常见的教程都是关于简单使用，场景一旦复杂就没什么教程了，之后有时间会写一之后有时间会写一之后有时间会写一之后有时间会写一期说说。",
                    color = colorResource(id = R.color.text_color),
                    fontSize = 12.sp,
                    lineHeight = 15.sp,
                    overflow= TextOverflow.Ellipsis
                )
            }
        }
    }
}