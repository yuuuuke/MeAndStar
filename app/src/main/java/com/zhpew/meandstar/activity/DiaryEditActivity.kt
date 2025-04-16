package com.zhpew.meandstar.activity

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.VisualMediaType
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
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
import com.zhpew.meandstar.vm.DateEditViewModel
import com.zhpew.meandstar.widget.CustomActionBar

class DiaryEditActivity : BaseActivity<DateEditViewModel>() {

    companion object {

        private const val DIARY_ID = "DIARY_ID"

        fun startAct(context: Context, id: Int = -1) {
            val intent = Intent(context, DiaryEditActivity::class.java)
            intent.putExtra(DIARY_ID, id)
            context.startActivity(intent)
        }
    }

    private lateinit var picker: ActivityResultLauncher<PickVisualMediaRequest>
    val data = mutableStateOf<DiaryEntity?>(null)
    val title = mutableStateOf<String>("")
    val content = mutableStateOf<String>("")

    override fun initData() {
        picker = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) {

        }
        val id = intent.getIntExtra(DIARY_ID, -1)

        if (id != -1) {
            vm.getDiaryById(1)
            vm.diaryLiveData.observe(this) {
                data.value = it
                title.value = data.value!!.title
                content.value = data.value!!.textContent
            }
        } else {
            data.value =
                DiaryEntity(0, 0, "", "", ArrayList<DiaryItemEntity>().toMutableStateList(), null)
        }

        vm.cuLiveData.observe(this) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
                onBackPress = {
                    onBackPressedDispatcher.onBackPressed()
                }, rightImg = painterResource(id = R.drawable.icon_done), rightIconPress = {
                    data.value!!.title = title.value
                    data.value!!.textContent = content.value
                    data.value!!.editTime = System.currentTimeMillis()
                    vm.updateOrCreateData(data.value!!)
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
                        data.value!!.diaryItem.forEach { item ->
                            if (item.type == 0) {
                                // 图片
                            } else {
                                // 文字
                                DiaryTextView(item)
                            }
                        }
                        Row {
                            Text(
                                text = "123",
                                modifier = Modifier.noAnimClick {
                                    // 添加一行文字
                                    data.value!!.diaryItem.add(
                                        DiaryItemEntity(
                                            1,
                                            "",
                                            data.value!!.diaryItem.size
                                        )
                                    )
                                }
                            )

                            Text(
                                text = "4656",
                                modifier = Modifier.noAnimClick {
                                    // 添加一行图片
                                    picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                }
                            )
                        }
                    })
            }
        }
    }

    @Composable
    fun DiaryTextView(item: DiaryItemEntity) {
//        Box(
//            modifier = Modifier
//                .padding(vertical = 10.dp, horizontal = 8.dp)
//                .clip(
//                    RoundedCornerShape(12.dp)
//                )
//                .background(colorResource(id = R.color.color_E46962)),
//            contentAlignment = Alignment.Center
//
//        ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .background(colorResource(id = R.color.color_E46962)),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = colorResource(id = R.color.text_color)
            ),
            value = item.text,
            onValueChange = { data.value?.diaryItem?.get(item.index)?.text = it },
        )
//        }
    }
}