package com.zhpew.meandstar.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.zhpew.meandstar.base.BaseActivity
import com.zhpew.meandstar.widget.CustomView.CustomWaveBgView

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentTime = System.currentTimeMillis()
        val day = (currentTime - 1672416000000) / 24 / 60 / 60 / 1000 + 1
        val day1 = (currentTime - 1663430400000) / 24 / 60 / 60 / 1000 + 1
        val day2 = (currentTime - 1664726400000) / 24 / 60 / 60 / 1000 + 1
        setContent {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
            ) {
                CustomWaveBgView(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                )
            }
        }
    }

    @Composable
    private fun Item(modifier: Modifier) {

    }
}