package com.zhpew.meandstar.activity

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.zhpew.meandstar.R
import com.zhpew.meandstar.base.BaseActivity
import com.zhpew.meandstar.base.BaseFragment
import com.zhpew.meandstar.vm.DateEditViewModel
import com.zhpew.meandstar.widget.ActionBar

class DateEditActivity : BaseActivity<DateEditViewModel>() {

    companion object{
        fun startAct(context:Context){
            val intent = Intent(context,DateEditActivity::class.java)
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
            ActionBar("12345"){
                finish()
            }
        }

    }

}