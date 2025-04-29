package com.zhpew.meandstar.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.zhpew.imagepickermodule.REQUEST_CODE
import com.zhpew.imagepickermodule.activity.SelectPicActivity
import com.zhpew.imagepickermodule.bean.PicItem
import com.zhpew.imagepickermodule.startPicker
import com.zhpew.meandstar.R
import com.zhpew.meandstar.base.BaseActivity
import com.zhpew.meandstar.db.dbEntity.DiaryEntity
import com.zhpew.meandstar.db.dbEntity.DiaryItemEntity
import com.zhpew.meandstar.ext.Go2Dp
import com.zhpew.meandstar.ext.noAnimClick
import com.zhpew.meandstar.utils.getScreenWidth
import com.zhpew.meandstar.vm.DateEditViewModel
import com.zhpew.meandstar.widget.CustomActionBar
import java.io.File
import java.util.Arrays

class DiaryEditActivity : BaseActivity<DateEditViewModel>() {

    companion object {

        private const val DIARY_ID = "DIARY_ID"

        fun startAct(context: Context, id: Int = -1) {
            val intent = Intent(context, DiaryEditActivity::class.java)
            intent.putExtra(DIARY_ID, id)
            context.startActivity(intent)
        }
    }

    private val imageSize = ((getScreenWidth().Go2Dp - 60).div(4)).toInt()

    val title = mutableStateOf<String>("")
    private val diaryItem = SnapshotStateList<DiaryItemEntity>()

    override fun initData() {

        val id = intent.getIntExtra(DIARY_ID, -1)

        if (id != -1) {
            vm.getDiaryById(1)
            vm.diaryLiveData.observe(this) {
                diaryItem.addAll(it.diaryItem.toMutableStateList())
                title.value = it.title
            }
        }

        vm.cuLiveData.observe(this) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

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
                onBackPress = {
                    onBackPressedDispatcher.onBackPressed()
                }, rightImg = painterResource(id = R.drawable.icon_done), rightIconPress = {
                    val data = DiaryEntity(
                        editTime = System.currentTimeMillis(),
                        title = title.value,
                        bg = null,
                        diaryItem = diaryItem.toList()
                    )
                    vm.updateOrCreateData(data)
                })
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
                        BasicTextField(
                            value = title.value,
                            onValueChange = {
                                if (it.length > 25)
                                    return@BasicTextField
                                title.value = it
                            },
                            modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp)
                                .fillMaxWidth(),
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.text_color)
                            )
                        )
                    }
                    diaryItem.forEach { item ->
                        if (item.type == 0) {
                            // 图片
                            DiaryImageView(item)
                        } else {
                            // 文字
                            DiaryTextView(item)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = "添加段落",
                            color= colorResource(R.color.white),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .height(30.dp)
                                .width(100.dp)
                                .background(colorResource(R.color.color_FF6D4A))
                                .padding(horizontal = 8.dp, vertical = 5.5.dp)
                                .noAnimClick {
                                    // 添加一行文字
                                    diaryItem.add(
                                        DiaryItemEntity(
                                            1,
                                            "",
                                            null,
                                            diaryItem.size
                                        )
                                    )
                                }
                        )

                        Text(
                            text = "添加图片",
                            color= colorResource(R.color.white),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .height(30.dp)
                                .width(100.dp)
                                .background(colorResource(R.color.color_FF6D4A))
                                .padding(horizontal = 8.dp, vertical = 5.5.dp)
                                .noAnimClick {
                                    // 添加一行图片
                                    startPicker {
                                        context = this@DiaryEditActivity
                                        maxImage = 4
                                    }
                                }
                        )
                    }
                })
        }
    }

    @Composable
    fun DiaryTextView(item: DiaryItemEntity) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 14.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .background(colorResource(id = R.color.color_E46962))
                .padding(8.dp),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = colorResource(id = R.color.text_color)
            ),
            value = item.text ?: "",
            onValueChange = {
                val itemData = diaryItem[item.index]
                diaryItem[item.index] = itemData.copy(text = it)
            },
        )
    }

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun DiaryImageView(item: DiaryItemEntity) {
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
            if ((item.images?.size ?: 0) < 4) {
                GlideImage(
                    model = R.drawable.img_add,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                        .width(imageSize.dp)
                        .height(imageSize.dp)
                        .border(
                            1.dp,
                            colorResource(id = R.color.color_22856C),
                            RoundedCornerShape(5.dp)
                        )
                        .padding(10.dp)
                        .noAnimClick {
                            //添加图片
                            startPicker {
                                context = this@DiaryEditActivity
                                maxImage = 4 - (item.images?.size ?: 0)
                                code = item.index
                            }
                        }
                )
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            val list =
                data!!.getSerializableExtra(SelectPicActivity.SELECTED_PIC) as ArrayList<PicItem>?
            if (list?.size == 0) {
                return
            }
            if (resultCode == Int.MAX_VALUE) {
                val images = ArrayList<String>()

                list?.map { item ->
                    item.url
                }?.let { images.addAll(it) }
                diaryItem.add(
                    DiaryItemEntity(
                        0,
                        "",
                        images,
                        diaryItem.size
                    )
                )
            } else {
                val images = diaryItem[resultCode].images ?: ArrayList()
                list?.map { url ->
                    url.toString()
                }?.let { images.addAll(it) }
                val itemData = diaryItem[resultCode]
                diaryItem[resultCode] = itemData.copy(images = images)
            }
        }
    }
}