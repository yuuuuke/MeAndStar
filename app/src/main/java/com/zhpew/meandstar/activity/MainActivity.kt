package com.zhpew.meandstar.activity

import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.zhpew.meandstar.base.BaseActivity
import com.zhpew.meandstar.vm.MainViewModel
import com.zhpew.meandstar.R
import com.zhpew.meandstar.fragment.DiaryFragment
import com.zhpew.meandstar.fragment.HomeFragment

class MainActivity : BaseActivity<MainViewModel>() {

//    companion object{
//        const val HOME = "HOME"
//        const val DIARY = "DIARY"
//    }

    private var mSelectedPage: MutableState<Int> = mutableStateOf(0)
    private lateinit var pageContainer: FrameLayout

    private val homeFragment = HomeFragment()
    private val diaryFragment = DiaryFragment()

    private val fragments = HashMap<Int, Fragment>()
//    private var currentFragment = HOME

    @Composable
    override fun InitComposeView() {
        setStatusBarColor(false)
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            AndroidView<FrameLayout>(
                factory = {
                    pageContainer = FrameLayout(it)
                    pageContainer.id = R.id.container_fragment
                    pageContainer
                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                BottomItem(
                    modifier = Modifier.weight(1F, true),
                    0,
                )
                BottomItem(
                    modifier = Modifier.weight(1F, true),
                    1,
                )
                BottomItem(
                    modifier = Modifier.weight(1F, true),
                    2,
                )
                BottomItem(
                    modifier = Modifier.weight(1F, true),
                    3,
                )
                BottomItem(
                    modifier = Modifier.weight(1F, true),
                    4,
                )
            }
        }
    }

    @Composable
    private fun BottomItem(modifier: Modifier, index: Int) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(colorResource(id = R.color.main_color))
                .clickable {
                    onItemSelected(index)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = getBottomItemItem(index),
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )
        }
    }

    @Composable
    private fun getBottomItemItem(index: Int): Painter {
        return when (index) {
            0 -> if (mSelectedPage.value == index) painterResource(id = R.drawable.icon_home_selected) else painterResource(
                id = R.drawable.icon_home_unselected
            )
            1 -> if (mSelectedPage.value == index) painterResource(id = R.drawable.icon_piggybank_selected) else painterResource(
                id = R.drawable.icon_piggybank_unselected
            )
            2 -> if (mSelectedPage.value == index) painterResource(id = R.drawable.icon_edit_selected) else painterResource(
                id = R.drawable.icon_edit_unselected
            )
            3 -> if (mSelectedPage.value == index) painterResource(id = R.drawable.icon_memorandum_selected) else painterResource(
                id = R.drawable.icon_memorandum_unselected
            )
            else -> if (mSelectedPage.value == index) painterResource(id = R.drawable.icon_calendar_selected) else painterResource(
                id = R.drawable.icon_calendar_unselected
            )

        }
    }

    private fun onItemSelected(index: Int) {
        if (mSelectedPage.value == index) {
            return
        }

        fragments[mSelectedPage.value]?.let {
            supportFragmentManager.beginTransaction().hide(it).commit()
        }

        mSelectedPage.value = index

        when (index) {
            0 ->
                if (fragments[index] == null) {
                    fragments[index] = homeFragment
                    supportFragmentManager.beginTransaction().add(pageContainer.id, homeFragment)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction().show(homeFragment).commit()
                }
            2 ->
                if (fragments[index] == null) {
                    fragments[index] = diaryFragment
                    supportFragmentManager.beginTransaction().add(pageContainer.id, diaryFragment)
                        .commit()
                } else {
                    supportFragmentManager.beginTransaction().show(diaryFragment).commit()
                }
        }
    }
}