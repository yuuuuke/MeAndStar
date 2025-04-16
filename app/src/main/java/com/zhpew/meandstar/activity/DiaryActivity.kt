package com.zhpew.meandstar.activity

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.zhpew.meandstar.R
import com.zhpew.meandstar.base.BaseActivity
import com.zhpew.meandstar.db.dbEntity.DiaryEntity
import com.zhpew.meandstar.ext.Go2Dp
import com.zhpew.meandstar.ext.noAnimClick
import com.zhpew.meandstar.vm.DiaryViewModel
import com.zhpew.meandstar.widget.CustomActionBar

class DiaryActivity : BaseActivity<DiaryViewModel>() {

    companion object {

        private const val DIARY_ID = "DIARY_ID"
        fun startAct(context: Context, id: Int) {
            val intent = Intent(context, DiaryActivity::class.java)
            intent.putExtra(DIARY_ID, id)
            context.startActivity(intent)
        }
    }

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
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(),
                    columns = GridCells.Fixed(3),
                    content = {
                        item(span = { GridItemSpan(3) }) {
                            Column {
                                Text(
                                    text = it.title,
                                    modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                                    color = colorResource(
                                        id = R.color.text_color
                                    ),
                                    fontSize = 16.sp,
                                    maxLines = 1,
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                        .background(colorResource(id = R.color.color_26AA89))
                                        .height(1.dp)
                                )
                                Text(
                                    text = it.textContent,
                                    modifier = Modifier.padding(top = 16.dp, start = 20.dp),
                                    color = colorResource(
                                        id = R.color.text_color
                                    ),
                                    fontSize = 13.sp,
                                )
                            }
                        }

//                        items(it.images?.size ?: 0) { index ->
//                            GlideImage(
//                                modifier = Modifier
//                                    .height(height.value.dp)
//                                    .padding(10.dp)
//                                    .clip(RoundedCornerShape(10.dp))
//                                    .noAnimClick {
//
//                                    },
//                                model = it.images?.get(index),
//                                contentDescription = "",
//                                contentScale = ContentScale.Crop
//                            )
//                        }
                    })
            }
        }
    }

}