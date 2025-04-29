package com.zhpew.meandstar.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.zhpew.meandstar.R
import com.zhpew.meandstar.base.BaseActivity
import com.zhpew.meandstar.db.dbEntity.DiaryEntity
import com.zhpew.meandstar.db.dbEntity.DiaryItemEntity
import com.zhpew.meandstar.ext.Go2Dp
import com.zhpew.meandstar.ext.noAnimClick
import com.zhpew.meandstar.utils.getScreenWidth
import com.zhpew.meandstar.vm.DiaryViewModel
import com.zhpew.meandstar.widget.CustomActionBar
import java.io.File

class DiaryActivity : BaseActivity<DiaryViewModel>() {

    companion object {

        private const val DIARY_ID = "DIARY_ID"
        fun startAct(context: Context, id: Int) {
            val intent = Intent(context, DiaryActivity::class.java)
            intent.putExtra(DIARY_ID, id)
            context.startActivity(intent)
        }
    }
    private val imageSize = ((getScreenWidth().Go2Dp - 60).div(4)).toInt()
    val data =
        mutableStateOf<DiaryEntity?>(null)

    override fun initData() {
        val id = intent.getIntExtra(DIARY_ID, -1)
        vm.getDiaryById(id)
        vm.diaryLiveData.observe(this) {
            data.value = it
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    override fun InitComposeView() {
        val height = remember {
            mutableStateOf(-1)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        if (height.value == -1) {
                            height.value = (placeable.width / 3).Go2Dp
                        }
                        placeable.placeRelative(0, 0)
                    }
                }
        ) {
            CustomActionBar(
                title = "",
                rightImg = painterResource(id = R.drawable.icon_edit),
                onBackPress = {
                    onBackPressedDispatcher.onBackPressed()
                },
                rightIconPress = {
                    // 去编辑界面去
                    DiaryEditActivity.startAct(this@DiaryActivity, id = data.value!!.id)
                })

            data.value?.let {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        Box(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 14.dp)
                                .height(52.dp)
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(12.dp)
                                )
                                .background(colorResource(R.color.color_EC928E)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = data.value!!.title,
                                color = colorResource(id = R.color.text_color),
                                fontSize = 16.sp
                            )
                        }
                        data.value!!.diaryItem.forEach {item ->
                            if (item.type == 0) {
                                // 图片
                                DiaryImageView(item)
                            } else {
                                // 文字
                                DiaryTextView(item)
                            }
                        }
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun DiaryImageView(item: DiaryItemEntity){
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
        ) {
            item.images?.forEach {
                GlideImage(
                    model = File(it),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                        .width(imageSize.dp)
                        .height(imageSize.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .border(
                            1.dp,
                            colorResource(id = R.color.color_22856C),
                            RoundedCornerShape(5.dp)
                        )
                        .noAnimClick {
                            // 查看大图
                        }
                )
            }
        }
    }

    @Composable
    fun DiaryTextView(item:DiaryItemEntity){
        Text(
            text = item.text ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 14.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .background(colorResource(id = R.color.color_E46962))
                .padding(8.dp),
            fontSize = 16.sp,
            color = colorResource(id = R.color.text_color)
        )
    }
}