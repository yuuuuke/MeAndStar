package com.zhpew.meandstar.fragment

import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhpew.meandstar.R
import com.zhpew.meandstar.base.BaseFragment
import com.zhpew.meandstar.vm.HomeViewModel
import com.zhpew.meandstar.widget.CustomActionBar
import com.zhpew.meandstar.widget.DatePickerView

class HomeFragment: BaseFragment<HomeViewModel>() {

    @Composable
    override fun InitComposeView(){
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .background(colorResource(id = R.color.main_color))
            ) {}
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(colorResource(id = R.color.main_color))
            ) {
            }
        }
    }
    
    
}