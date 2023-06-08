package com.zhpew.meandstar.widget

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.zhpew.meandstar.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDateDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .width(250.dp)
                .height(330.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(id = R.color.main_color))
        ) {
            Text(
                text = stringResource(id = R.string.choose_date),
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
                    .background(colorResource(id = R.color.white))
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.choose_date),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.black),
                    fontSize = 12.sp,
                )
            }
            Text(
                text = stringResource(id = R.string.thing),
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp)
                    .fillMaxWidth(),
                color = colorResource(id = R.color.black),
                fontSize = 14.sp
            )
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorResource(id = R.color.white))
            )
            Box(modifier = Modifier.weight(1f, true))
            Row(
                modifier = Modifier
                    .height(55.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f, true)
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(bottomStart = 12.dp)
                        )
                        .background(colorResource(id = R.color.color_EC928E))
                )
                Text(
                    text = stringResource(id = R.string.ok),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f, true)
                        .fillMaxHeight()
                        .clip(
                            RoundedCornerShape(bottomEnd = 12.dp)
                        )
                        .background(colorResource(id = R.color.color_E46962))
                )
            }
        }
    }
}

//class AddDateDialog(context:Context): Dialog(context,R.style.CommonDialog) {
//
//    private val mChooseDate:View
//    private val mTvDate:TextView
//    private val mTvOk:TextView
//    private val mTvCancel:TextView
//    private val mEtName:EditText
//
//    init {
//        setContentView(R.layout.dialog_add_data)
//        mChooseDate = findViewById(R.id.card_choose_date)
//        mTvDate = findViewById(R.id.tv_choose_date)
//        mTvOk = findViewById(R.id.tv_ok)
//        mTvCancel = findViewById(R.id.tv_cancel)
//        mEtName = findViewById(R.id.et_name)
//
//        mChooseDate.setOnClickListener{
//
//        }
//
//        mTvOk.setOnClickListener {
//
//        }
//        mTvCancel.setOnClickListener {
//            dismiss()
//        }
//    }
//
//}