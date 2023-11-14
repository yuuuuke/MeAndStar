package com.zhpew.meandstar.activity

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import com.zhpew.meandstar.utils.Go2Dp
import com.zhpew.meandstar.utils.noAnimClick
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

    lateinit var picker: ActivityResultLauncher<PickVisualMediaRequest>
    val data = mutableStateOf<DiaryEntity?>(null)
    val title = mutableStateOf<String>( "")
    val content = mutableStateOf<String>("")

    override fun initData() {
        picker = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) {
            val images = ArrayList(data.value?.images ?: ArrayList())
            for (uri in it) {
                images.add(uri.toString())
            }
            data.value = data.value?.copy(images = images)
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
            data.value = DiaryEntity(0, 0, "", "", null, null)
        }

        vm.cuLiveData.observe(this) {
            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show()
            val intent = Intent(this,MainActivity::class.java)
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
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(),
                    columns = GridCells.Fixed(3),
                    content = {
                        item(span = { GridItemSpan(3) }) {
                            Column {
                                BasicTextField(
                                    value = title.value,
                                    onValueChange = { title.value = it },
                                    modifier = Modifier
                                        .padding(top = 15.dp, start = 20.dp)
                                        .fillMaxWidth(),
                                    singleLine = true,
                                    textStyle = TextStyle(
                                        fontSize = 16.sp,
                                        color = colorResource(id = R.color.text_color)
                                    )
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 10.dp, start = 15.dp, end = 15.dp)
                                        .background(colorResource(id = R.color.color_26AA89))
                                        .height(1.dp)
                                )
                                BasicTextField(
                                    value = content.value,
                                    onValueChange = { content.value = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .defaultMinSize(minHeight = 400.dp)
                                        .padding(top = 16.dp, start = 20.dp),
                                    textStyle = TextStyle(
                                        fontSize = 15.sp,
                                        color = colorResource(id = R.color.text_color)
                                    )
                                )
                            }
                        }
                        items((it.images?.size ?: 0) + 1) { index ->
                            if (index == (it.images?.size ?: 0)) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .height(height.value.dp)
                                        .padding(10.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(
                                            1.dp,
                                            colorResource(id = R.color.text_color),
                                            RoundedCornerShape(10.dp)
                                        )
                                        .noAnimClick {
//                                            //选图界面
                                            picker.launch(
                                                PickVisualMediaRequest(
                                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                                )
                                            )
                                        }
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.img_add),
                                        contentDescription = "",
                                        modifier = Modifier
                                            .width(70.dp)
                                            .height(70.dp)
                                    )
                                }
                            } else {
                                GlideImage(
                                    modifier = Modifier
                                        .height(height.value.dp)
                                        .padding(10.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    model = it.images?.get(index),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    })
            }
        }
    }
}