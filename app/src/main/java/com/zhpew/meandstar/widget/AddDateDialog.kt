package com.zhpew.meandstar.widget

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.zhpew.meandstar.R
import com.zhpew.meandstar.db.dbEntity.CommemorationDayEntity
import com.zhpew.meandstar.utils.noAnimClick
import com.zhpew.meandstar.utils.time2String

val nameValue = mutableStateOf( "")
val dateValue = mutableStateOf( 0L)

@SuppressLint("UnrememberedMutableState")
@Composable
fun AddDateDialog(
    data: CommemorationDayEntity?,
    onResult: (data: CommemorationDayEntity) -> Unit,
    onDismiss: () -> Unit,
    chooseDate: (onResult: (date: String) -> Unit) -> Unit
) {
    Log.v("zwp","in + ${data?.id}")
    nameValue.value = data?.content?:""
    dateValue.value = data?.date?:0
    Dialog(onDismissRequest = { }) {
        Column(
            modifier = Modifier
                .width(250.dp)
                .height(330.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(id = R.color.main_color))
        ) {
            Text(
                text = stringResource(id = R.string.add_date),
                modifier = Modifier
                    .padding(top = 28.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.black),
                fontSize = 14.sp
            )
            Text(
                text = stringResource(id = R.string.date),
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp)
                    .fillMaxWidth(),
                color = colorResource(id = R.color.black),
                fontSize = 14.sp
            )
            Card(
                modifier = Modifier
                    .padding(top = 14.dp, start = 14.dp, end = 20.dp)
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(colorResource(id = R.color.white))
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .noAnimClick {
                            chooseDate {
                                dateValue.value = it.toLong()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (dateValue.value == 0L) stringResource(id = R.string.choose_date) else time2String(dateValue.value),
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.black),
                        fontSize = 12.sp,
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.thing),
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp)
                    .fillMaxWidth(),
                color = colorResource(id = R.color.black),
                fontSize = 14.sp
            )
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(id = R.color.white))
            ) {
                BasicTextField(
                    value = nameValue.value,
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.black)
                    ),
                    onValueChange = { nameValue.value = it },
                    singleLine = true,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                )
            }
            Box(modifier = Modifier.weight(1f, true))
            Row(
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f, true)
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(bottomStart = 12.dp)
                        )
                        .background(colorResource(id = R.color.color_EC928E))
                        .noAnimClick {
                            onDismiss()
                        }
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f, true)
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(bottomEnd = 12.dp)
                        )
                        .background(colorResource(id = R.color.color_E46962))
                        .noAnimClick {
                            data?.let {
                                //更新
                                data.date = dateValue.value
                                data.content = nameValue.value
                                onResult(data)
                                return@noAnimClick
                            }

                            onResult(
                                CommemorationDayEntity(
                                    nameValue.value,
                                    dateValue.value,
                                )
                            )
                        }
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}