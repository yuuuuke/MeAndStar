package com.zhpew.meandstar.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.zhpew.meandstar.R
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar

data class DatePickerState(var year: String = "", var month: String = "", var day: String = "")


val years = (1949..2051).map { if (it == 1949 || it == 2051) "" else it.toString() }
val days31 = (0..32).map { if (it == 0 || it == 32) "" else it.toString() }
val days30 = (0..31).map { if (it == 0 || it == 31) "" else it.toString() }
val days29 = (0..30).map { if (it == 0 || it == 30) "" else it.toString() }
val days28 = (0..29).map { if (it == 0 || it == 29) "" else it.toString() }
val monthsNames = (0..13).map { if (it == 0 || it == 13) "" else it.toString() }

var currentMonth = mutableStateOf(days31)

@Composable
fun DatePickerView(
    title: String,
    date: DatePickerState?,
    dateStateListener: (state: DatePickerState) -> Unit
) {
    var state: DatePickerState

    date?.let {
        state = date
    }

    val year = remember {
        mutableStateOf(date?.year ?: years[Calendar.getInstance().get(Calendar.YEAR) - 1949])
    }
    val month = remember {
        mutableStateOf(date?.month ?: monthsNames[Calendar.getInstance().get(Calendar.MONTH) + 1])
    }
    val day = remember {
        mutableStateOf(date?.day ?: currentMonth.value[Calendar.getInstance().get(Calendar.DAY_OF_MONTH)])
    }

    state = DatePickerState(year.toString(), month.toString(), day.toString())

    LaunchedEffect(key1 = state, block = {
        dateStateListener(state)
    })

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {

        Text(
            text = title,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(120.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SelectedItem(data = years, year.value) { state.year = it }
            SelectedItem(data = monthsNames, month.value) {
                if (it == "1" || it == "3" || it == "5" || it == "7" || it == "8" || it == "10" || it == "12") {
                    // 31 天月
                    if (currentMonth.value != days31) {
                        currentMonth.value = days31
                        state.month = it
                        state.day = "1"
                    }
                } else if (it == "2") {
                    // 2月
                    if (state.year.toInt() % 4 == 0) {
                        // 闰年
                        if (currentMonth.value != days28) {
                            currentMonth.value = days28
                            state.month = it
                            state.day = "1"
                        }
                    } else {
                        if (currentMonth.value != days29) {
                            currentMonth.value = days29
                            state.month = it
                            state.day = "1"
                        }
                    }
                } else {
                    // 30天月
                    if (currentMonth.value != days30) {
                        currentMonth.value = days30
                        state.month = it
                        state.day = "1"
                    }
                }
            }
            SelectedItem(
                data = currentMonth.value,
                day.value
            ) { state.day = it }
        }

    }
}

@Composable
private fun SelectedItem(
    data: List<String>,
    initData: String,
    onSelected: suspend (target: String) -> Unit
) {

    val state = rememberLazyListState()

    LaunchedEffect(key1 = state.isScrollInProgress, block = {
        var target = state.firstVisibleItemIndex
        if (!state.isScrollInProgress) {
            // 滚动结束了调用
            if (state.firstVisibleItemScrollOffset > 40) {
                //滚动到下一个
                target = state.firstVisibleItemIndex + 1
            }
            state.animateScrollToItem(target)
        }
        onSelected(data[target + 1])
    })

    LazyColumn(modifier = Modifier.fillMaxHeight(), content = {
        items(data.size) {
            Column(
                modifier = Modifier
                    .height(40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = data[it], color = colorResource(id = R.color.black))
            }
        }
    }, state = state)
}
