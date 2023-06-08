package com.zhpew.meandstar.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.zhpew.meandstar.R

val years = (1949..2051).map { if (it == 1949 || it == 2051) "" else it.toString() }
val days = (0..32).map { if (it == 0 || it == 32) "" else it.toString() }
val monthsNames = listOf(
    "",
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Oct",
    "Nov",
    "Dec",
    ""
)

@Composable
fun DatePickerView(title: String) {
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
                .height(120.dp)
        ) {
            SelectedItem(data = years, modifier = Modifier.weight(1f,true))
            SelectedItem(data = monthsNames, modifier = Modifier.weight(1f,true))
            SelectedItem(data = days, modifier = Modifier.weight(1f,true))
        }

    }
}

@Composable
private fun SelectedItem(data: List<String>, modifier: Modifier) {
    LazyColumn(modifier = modifier.fillMaxHeight(), content = {
        items(data.size) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = data[it], color = colorResource(id = R.color.black))
            }
        }
    })
}
