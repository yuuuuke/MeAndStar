package com.zhpew.meandstar.activity

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zhpew.meandstar.base.BaseActivity
import com.zhpew.meandstar.vm.DateEditViewModel
import com.zhpew.meandstar.widget.CustomActionBar

class DiaryEditActivity : BaseActivity<DateEditViewModel>() {

    companion object{
        fun startAct(context:Context,id:Int){
            val intent = Intent(context,DiaryEditActivity::class.java)
            context.startActivity(intent)
        }
    }

    @Composable
    override fun InitComposeView() {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            CustomActionBar("12345"){
                finish()
            }
        }

    }

}