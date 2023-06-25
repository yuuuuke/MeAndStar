package com.zhpew.meandstar.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.zhpew.meandstar.base.BaseActivity
import com.zhpew.meandstar.db.dbEntity.DiaryEntity
import com.zhpew.meandstar.vm.DiaryViewModel
import com.zhpew.meandstar.widget.CustomActionBar

class DiaryActivity : BaseActivity<DiaryViewModel>() {

    @Composable
    override fun InitComposeView() {

        val data = remember {
            mutableStateOf<DiaryEntity?>(null)
        }

        vm.getDiaryById(1)
        vm.diaryLiveData.observe(this){
            data.value = it
        }

        ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {

            val (title,content) = createRefs()

            CustomActionBar("") {
                onBackPressedDispatcher.onBackPressed()
            }
            data.value?.let{

            }
        }
    }

}