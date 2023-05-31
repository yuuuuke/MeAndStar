package com.zhpew.meandstar.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhpew.meandstar.R
import com.zhpew.meandstar.base.BaseFragment
import com.zhpew.meandstar.vm.DateViewModel

/**
 * 纪念日
 */
class DateFragment : BaseFragment<DateViewModel>() {

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
                    .background(colorResource(id = R.color.main_color))
            ) {}
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(colorResource(id = R.color.main_color))
            ) {
                Text(text = "纪念日", color = colorResource(id = R.color.text_color), fontSize = 18.sp)
            }
            HeaderView()
            ListData()
        }
    }

    @Composable
    private fun HeaderView() {
        Row(
            modifier = Modifier
                .background(colorResource(id = R.color.main_color))
                .padding(top = 30.dp, bottom = 30.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Column() {
                Text(
                    modifier = Modifier.padding(bottom = 5.dp),
                    text = "那一天已经",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black)
                )
                Text(
                    text = "起始日: 2020-08-08 星期六",
                    fontSize = 14.sp,
                    color = colorResource(id = R.color.sub_text_color)
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(end = 5.dp),
                    text = "600",
                    color = colorResource(id = R.color.text_color),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "天",
                    color = colorResource(id = R.color.hint_text_color),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    private fun ListData() {
        LazyColumn(content = {
            items(10) {
                Row(modifier = Modifier) {
                    
                }
            }
        }
        )
    }

    @Composable
    private fun Item(){

    }
}