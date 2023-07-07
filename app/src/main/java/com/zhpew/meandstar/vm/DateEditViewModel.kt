package com.zhpew.meandstar.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zhpew.meandstar.app.MyApplication
import com.zhpew.meandstar.base.BaseViewModel
import com.zhpew.meandstar.db.dbEntity.DiaryEntity
import com.zhpew.meandstar.repository.DiaryRepository
import kotlinx.coroutines.launch

class DateEditViewModel: BaseViewModel() {

    private val _diaryLiveData: MutableLiveData<DiaryEntity> = MutableLiveData()
    val diaryLiveData: LiveData<DiaryEntity> = _diaryLiveData

    private val _deleteLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val cuLiveData: LiveData<Boolean> = _deleteLiveData

    val repository: DiaryRepository by lazy {
        MyApplication.instance.repository.diaryRepository
    }

    fun getDiaryById(id:Int){
        viewModelScope.launch {
            _diaryLiveData.postValue(repository.getDiaryById(id))
        }
    }

    fun updateOrCreateData(data:DiaryEntity){
        viewModelScope.launch {
            repository.updateData(data)
            _deleteLiveData.postValue(true)
        }
    }

}