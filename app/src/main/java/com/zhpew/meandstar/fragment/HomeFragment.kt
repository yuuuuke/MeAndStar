package com.zhpew.meandstar.fragment

import androidx.compose.runtime.Composable
import com.zhpew.meandstar.base.BaseFragment
import com.zhpew.meandstar.vm.HomeViewModel
import com.zhpew.meandstar.widget.DatePickerView

class HomeFragment: BaseFragment<HomeViewModel>() {

    @Composable
    override fun InitComposeView(){
        DatePickerView("日期选择")
    }

}