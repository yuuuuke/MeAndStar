package com.zhpew.meandstar.fragment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
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
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 24.dp)
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
            Column(verticalArrangement = Arrangement.Bottom) {
                Text(text = "", color = colorResource(id = R.color.text_color), fontSize = 12.sp)
                Text(text = "", color = colorResource(id = R.color.text_color), fontSize = 14.sp)
                Text(text = "", color = colorResource(id = R.color.text_color), fontSize = 16.sp)
            }
            Box(
                modifier = Modifier
                    .border(
                        BorderStroke(
                            12.dp,
                            colorResource(id = R.color.main_color)
                        )
                    )
                    .padding(10.dp)
            ) {
                Text(
                    text = "adasfas",
                    color = colorResource(id = R.color.text_color),
                    fontSize = 12.sp
                )
            }
        }
    }
}