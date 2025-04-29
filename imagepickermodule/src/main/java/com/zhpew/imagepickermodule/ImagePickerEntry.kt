package com.zhpew.imagepickermodule

import android.app.Activity
import android.content.Intent
import com.zhpew.imagepickermodule.activity.SelectPicActivity
import java.io.Serializable

const val REQUEST_CODE = 10086

class ImagePickerEntry {
    var context: Activity? = null
    var code = Int.MAX_VALUE
    var maxImage: Int = Int.MAX_VALUE
}

fun startPicker(block: ImagePickerEntry.() -> Unit) {
    val entry = ImagePickerEntry().apply(block)
    entry.context?.let {
        val intent = Intent(it, SelectPicActivity::class.java)
        intent.putExtra("code", entry.code)
        intent.putExtra("count", entry.maxImage)
        it.startActivityForResult(intent, REQUEST_CODE)
    }
}
