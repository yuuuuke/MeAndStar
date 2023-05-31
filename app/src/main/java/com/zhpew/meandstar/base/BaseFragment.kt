package com.zhpew.meandstar.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    private lateinit var vm: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        if (getLayoutId() == -1) {
            return ComposeView(requireContext()).apply {
                setContent {
                    InitComposeView()
                }
            }
        }
        return inflater.inflate(getLayoutId(), container, false)
    }

    private fun initViewModel(): T {
        return ViewModelProvider(this)[(this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>]
    }

    @Composable
    open fun InitComposeView() {

    }

    open fun getLayoutId(): Int {
        return -1
    }
}