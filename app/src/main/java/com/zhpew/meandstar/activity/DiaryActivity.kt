package com.zhpew.meandstar.activity

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.zhpew.meandstar.R
import com.zhpew.meandstar.base.BaseActivity
import com.zhpew.meandstar.db.dbEntity.DiaryEntity
import com.zhpew.meandstar.vm.DiaryViewModel
import com.zhpew.meandstar.widget.CustomActionBar

class DiaryActivity : BaseActivity<DiaryViewModel>() {

    companion object{
        fun startAct(context: Context, id:Int){
            val intent = Intent(context,DiaryActivity::class.java)
            context.startActivity(intent)
        }
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    override fun InitComposeView() {

        val data = remember {
            mutableStateOf<DiaryEntity?>(null)
        }

        vm.getDiaryById(1)
        vm.diaryLiveData.observe(this) {
            data.value = it
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            CustomActionBar(
                title = "",
                rightImg = painterResource(id = R.drawable.icon_edit),
                onBackPress = {
                    onBackPressedDispatcher.onBackPressed()
                },
                rightIconPress = {
                    // 去编辑界面去
                    DiaryEditActivity.startAct(this@DiaryActivity, id = 1)
                })
            data.value?.let {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    columns = GridCells.Fixed(3),
                    content = {
                        item(span = { GridItemSpan(2) }) {
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
                                    .height(1.dp)
                            )
                            Text(
                                text = it.title,
                                modifier = Modifier.padding(top = 16.dp, start = 20.dp),
                                color = colorResource(
                                    id = R.color.text_color
                                ),
                                fontSize = 13.sp,
                            )
                        }

                        items(it.images?.size?:1) { index ->
//                            GlideImage(
//                                modifier = Modifier
//                                    .height(100.dp),
//                                model = it.images[index],
//                                contentDescription = "",
//                                contentScale = ContentScale.Crop
//                            )
                        }
                    })
            }
        }
    }

}