package com.zhpew.meandstar.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM : BaseViewModel> : FragmentActivity() {

    lateinit var vm: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.decorView.systemUiVisibility = option or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        vm = initViewModel()
        if (getLayoutId() == -1) {
            setContent {
                InitComposeView()
            }
        } else {
            setContentView(getLayoutId())
        }
        initData()
    }

    protected fun setStatusBarColor(isBlack:Boolean) {
        if(isBlack){
            val flags = window.decorView.systemUiVisibility
            window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }else{
            val flags = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.decorView.systemUiVisibility = flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    @Composable
    open fun InitComposeView() {

    }

    open fun getLayoutId(): Int {
        return -1
    }

    open fun initData(){

    }

    private fun initViewModel(): VM {
        return ViewModelProvider(this)[(this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>]
    }
}