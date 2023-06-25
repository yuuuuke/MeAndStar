package com.zhpew.meandstar.fragment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.zhpew.meandstar.R
import com.zhpew.meandstar.base.BaseFragment
import com.zhpew.meandstar.db.dbEntity.CommemorationDayEntity
import com.zhpew.meandstar.utils.getWeekOfDate
import com.zhpew.meandstar.utils.noAnimClick
import com.zhpew.meandstar.utils.time2String
import com.zhpew.meandstar.vm.DateViewModel
import com.zhpew.meandstar.vm.DateUiState
import com.zhpew.meandstar.widget.AddDateDialog
import com.zhpew.meandstar.widget.DatePickerState
import com.zhpew.meandstar.widget.DatePickerView
import com.zhpew.meandstar.widget.longToDatePickerState
import kotlin.math.abs
import kotlin.math.ceil

/**
 * 纪念日
 */
class DateFragment : BaseFragment<DateViewModel>() {

    companion object {
        const val ONE_DAY = 24 * 60 * 60 * 1000L
    }

    private val currentTime = System.currentTimeMillis()
    private val showAddDataDialog = mutableStateOf(false)
    private val showDatePicker = mutableStateOf(false)

    private var clickedItem = mutableStateOf<CommemorationDayEntity?>(null)

    @Composable
    override fun InitComposeView() {

        val uiState = vm.uiState

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
            uiState.value.headerData?.let {
                HeaderView(uiState)
            }
            ListData(uiState)

            AddDate()
            DatePicker()
        }
    }

    @Composable
    private fun HeaderView(uiState: MutableState<DateUiState>) {
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
                    text = if (uiState.value.headerData!!.date > currentTime) uiState.value.headerData!!.content + "还有" else uiState.value.headerData!!.content + "已经",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black)
                )
                Text(
                    text = "起始日: ${time2String(uiState.value.headerData!!.date)} ${getWeekOfDate(uiState.value.headerData!!.date)}",
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
                    text = ceil((abs(uiState.value.headerData!!.date - currentTime)) / ONE_DAY.toDouble()).toInt()
                        .toString(),
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
    private fun ListData(uiState: MutableState<DateUiState>) {
        LazyColumn(content = {
            items(uiState.value.data.size) { it ->
                if (it == 0) {
                    Box(modifier = Modifier.height(8.dp)) {}
                }
                Card(
                    elevation = 2.dp,
                    contentColor = colorResource(id = R.color.main_color),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 14.dp)
                        .height(52.dp)
                        .fillMaxWidth()
                        .noAnimClick {
                            showAddDataDialog.value = true
                            clickedItem.value = uiState.value.data[it]
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 15.dp)
                                .weight(1f),
                            text = if (uiState.value.data[it].date > currentTime) uiState.value.data[it].content + "还有" else uiState.value.data[it].content + "已经",
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.black)
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .width(74.dp)
                                .fillMaxHeight()
                                .background(
                                    colorResource(id = if (uiState.value.data[it].date > currentTime) R.color.color_26AA89 else R.color.color_E46962)
                                )
                        ) {
                            Text(
                                text = ceil((abs(uiState.value.data[it].date - currentTime)) / ONE_DAY.toDouble()).toInt()
                                    .toString(),
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .width(52.dp)
                                .fillMaxHeight()
                                .background(
                                    colorResource(id = if (uiState.value.data[it].date > currentTime) R.color.color_22856C else R.color.color_B63028)
                                )
                        ) {
                            Text(
                                text = "天",
                            )
                        }
                    }
                }
            }
        }
        )
    }

    @Composable
    private fun AddDate() {
        if (showAddDataDialog.value) {
            AddDateDialog(clickedItem.value, {
                Log.v("zwp", "更换！！" + it.toString() + it.id)
                vm.updateOrInsertData(it)
                clickedItem.value!!.date
                showAddDataDialog.value = false
            }, {
                showAddDataDialog.value = false
            }, {
                //打开日期选择器
                showDatePicker.value = true
            })
        }
    }

    @Composable
    private fun DatePicker() {
        if (showDatePicker.value) {
            var state: DatePickerState? = null
            Dialog(onDismissRequest = {
                showDatePicker.value = false
                if (clickedItem.value == null) {
                    clickedItem.value =
                        CommemorationDayEntity(content = "", date = state!!.toLong())
                } else {
                    Log.v("zwp","clickedItem.value"+clickedItem.value!!.id)
                    clickedItem.value = clickedItem.value!!.copy(date = state!!.toLong())
                    Log.v("zwp", "///" + clickedItem.value!!.id)
                }
            }) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .height(260.dp),
                ) {
                    DatePickerView(
                        title = "选择日期",
                        date = if (clickedItem.value == null) null else longToDatePickerState(
                            clickedItem.value!!.date
                        )
                    ) {
                        state = it
                    }
                }
            }
        }
    }

    /**
     * fab点击
     */
    override fun onFABClick() {
        showAddDataDialog.value = true
        clickedItem.value = null
    }
}