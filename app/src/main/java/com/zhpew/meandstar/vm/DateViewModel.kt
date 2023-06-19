package com.zhpew.meandstar.vm

import androidx.lifecycle.viewModelScope
import com.zhpew.meandstar.app.MyApplication
import com.zhpew.meandstar.base.BaseViewModel
import com.zhpew.meandstar.db.dbEntity.CommemorationDayEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DateViewModel : BaseViewModel() {

    private val _allDataFlow = MutableStateFlow(ArrayList<CommemorationDayEntity>())
    val allDataFlow: StateFlow<ArrayList<CommemorationDayEntity>> = _allDataFlow

    private val repository by lazy {
        MyApplication.instance.repository.commemorationRepository
    }

    fun getAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            _allDataFlow.emit(repository.getAllData() as ArrayList<CommemorationDayEntity>)
        }
    }

    fun updateOrInsertData(entity: CommemorationDayEntity) {
        viewModelScope.launch {
            repository.updateData(entity)
        }
    }

    fun deleteData(entity: CommemorationDayEntity) {
        viewModelScope.launch {
            repository.deleteData(entity)
        }
    }

}