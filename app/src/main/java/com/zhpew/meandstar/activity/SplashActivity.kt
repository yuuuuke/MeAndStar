package com.zhpew.meandstar.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.zhpew.meandstar.R
import com.zhpew.meandstar.base.BaseActivity
import com.zhpew.meandstar.bean.ItemBean
import com.zhpew.meandstar.widget.CustomView.CustomWaveBgView
import java.lang.Thread.sleep

@SuppressLint("CustomSplashScreen")
class SplashActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        },500)
    }

    @Preview
    @Composable
    private fun InitView() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            val ( list, image) = createRefs()
            Image(
                painter = painterResource(id = R.mipmap.main_bg),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
            )
            CustomWaveBgView(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            )

            val data = ArrayList<ItemBean>()
            val time = (System.currentTimeMillis() - 1663430400000) / 24 / 60 / 60 / 1000 + 1
            val data1 = ItemBean("相遇 2022 年 9月 18日", "$time 天", R.drawable.default_bg_1)
            data.add(data1)

            val time1 = (System.currentTimeMillis() - 1664726400000) / 24 / 60 / 60 / 1000 + 1
            val data2 = ItemBean("相见 2022 年 10月 3日", "$time1 天", R.drawable.default_bg_2)
            data.add(data2)

            val time2 = (System.currentTimeMillis() - 1672416000000) / 24 / 60 / 60 / 1000 + 1
            val data3 = ItemBean("相爱 2022 年 12月 31日", "$time2 天", R.drawable.default_bg_3)
            data.add(data3)

            LazyColumn(
                modifier = Modifier.constrainAs(list) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.padding(top = 100.dp),
                content = {
                    items(data.size) {
                        Item(data[it].title, data[it].content, data[it].bg)
                    }
                })

            Image(
                painter = painterResource(id = R.mipmap.item1_left),
                contentDescription = "",
                modifier = Modifier
                    .constrainAs(image) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
            )
        }
    }

    @Composable
    private fun Item(
        titleString: String,
        contentString: String,
        bg: Int
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .height(Dp(150f))
                .padding(top = 10.dp, start = 20.dp, end = 20.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
            ) {
                val (title, content) = createRefs()
                createVerticalChain(title, content, chainStyle = ChainStyle.Packed)
                Image(
                    painter = painterResource(id = bg),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .alpha(0.6f)
                )
                Text(
                    text = titleString,
                    Modifier
                        .constrainAs(title) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text(
                    text = contentString,
                    Modifier
                        .constrainAs(content) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(title.bottom)
                        },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}