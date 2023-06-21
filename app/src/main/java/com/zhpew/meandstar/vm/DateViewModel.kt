package com.zhpew.meandstar.vm

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.zhpew.meandstar.app.MyApplication
import com.zhpew.meandstar.base.BaseViewModel
import com.zhpew.meandstar.db.dbEntity.CommemorationDayEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DateViewModel : BaseViewModel() {

    val uiState: MutableState<UiState> = mutableStateOf(UiState())

    private val repository by lazy {
        MyApplication.instance.repository.commemorationRepository
    }

    init {
        getAllData()
    }

    fun getAllData() {
        viewModelScope.launch {
            flow {
                emit(repository.getAllData())
            }.collect {
                for (temp in it) {
                    if (temp.showInHeader) {
                        uiState.value =
                            uiState.value.copy(data = it.toMutableStateList(), headerData = temp)
                        return@collect
                    }
                }
                if (it.isNotEmpty()) {
                    uiState.value =
                        uiState.value.copy(data = it.toMutableStateList(), headerData = it[0])
                }
            }
        }
    }

    fun updateOrInsertData(entity: CommemorationDayEntity) {
        viewModelScope.launch {
//            flow {
//                emit(repository.updateData(entity))
//            }.map {
//                val data = uiState.value.data
//                var index = -1
//                for ((i, temp) in data.withIndex()) {
//                    if (temp.timeStamp == entity.timeStamp) {
//                        index = i
//                        break
//                    }
//                }
//                if (index == -1) {
//                    data.add(entity)
//                } else {
//                    data[index] = entity
//                }
//                data.sortByDescending {
//                    it.date
//                }
//                data
//            }.collect {
//                uiState.value = uiState.value.copy(data = it.toMutableStateList())
//            }
            flow {
                emit(repository.updateData(entity))
            }.collect{
                getAllData()
            }
        }
    }

    fun deleteData(entity: CommemorationDayEntity) {
        viewModelScope.launch {
            repository.deleteData(entity)
        }
    }
}

data class UiState(
    val data: SnapshotStateList<CommemorationDayEntity> = mutableStateListOf(),
    val headerData: CommemorationDayEntity? = null
)