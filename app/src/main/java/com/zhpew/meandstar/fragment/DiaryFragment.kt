package com.zhpew.meandstar.fragment

import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import com.zhpew.meandstar.activity.DiaryActivity
import com.zhpew.meandstar.activity.DiaryEditActivity
import com.zhpew.meandstar.base.BaseFragment
import com.zhpew.meandstar.utils.getWeekOfDate
import com.zhpew.meandstar.utils.noAnimClick
import com.zhpew.meandstar.utils.time2String
import com.zhpew.meandstar.vm.DiaryListAction
import com.zhpew.meandstar.vm.DiaryListViewModel

class DiaryFragment : BaseFragment<DiaryListViewModel>() {

    override suspend fun initData(){
        vm.dispatch(DiaryListAction.GetAllDiary)
        vm.viewEvent.collect {

        }
    }

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
                Text(text = "日记本", color = colorResource(id = R.color.text_color), fontSize = 18.sp)
            }
            ListData()
        }
    }

    override fun onFABClick() {
        // 写日记
        DiaryEditActivity.startAct(requireContext())
    }

    @Composable
    private fun ListData() {
        LazyColumn(modifier = Modifier
            .background(colorResource(id = R.color.bg_color))
            .fillMaxWidth()
            .fillMaxHeight(), content = {
            items(vm.state.value.data.size) {
                Item(it)
            }
        })
    }

    @Composable
    private fun Item(index:Int) {
        val data = vm.state.value.data[index]
        Row(modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .noAnimClick {
                // 进入日记详情
                DiaryActivity.startAct(this.requireContext(), data.id)
            }) {
            Column(
                verticalArrangement = Arrangement.Bottom, modifier = Modifier
                    .padding(end = 16.dp)
                    .height(100.dp)
            ) {
                Text(text = getWeekOfDate(data.editTime), color = colorResource(id = R.color.text_color), fontSize = 12.sp)
                Text(
                    text = time2String(data.editTime,"MM月dd日"),
                    color = colorResource(id = R.color.text_color),
                    fontSize = 14.sp
                )
                Text(
                    text = time2String(data.editTime,"HH:mm"),
                    color = colorResource(id = R.color.text_color),
                    fontSize = 18.sp
                )
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
                    text = data.title,
                    color = colorResource(id = R.color.text_color),
                    maxLines = 1,
                    fontSize = 13.sp,
                    lineHeight = 15.sp,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = data.textContent,
                    color = colorResource(id = R.color.text_color),
                    fontSize = 12.sp,
                    lineHeight = 15.sp,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}