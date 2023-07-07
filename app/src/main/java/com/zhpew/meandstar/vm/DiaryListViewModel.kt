package com.zhpew.meandstar.vm

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.zhpew.meandstar.app.MyApplication
import com.zhpew.meandstar.base.BaseViewModel
import com.zhpew.meandstar.db.dbEntity.DiaryEntity
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DiaryListViewModel : BaseViewModel() {
    private val _viewEvent = Channel<DiaryListEvent>()
    private val repository by lazy {
        MyApplication.instance.repository.diaryRepository
    }

    val viewEvent = _viewEvent.receiveAsFlow()
    val state = mutableStateOf(UiState())

    fun dispatch(action: DiaryListAction) {
        when (action) {
            is DiaryListAction.GetAllDiary -> getAllDiary()
        }
    }

    private fun getAllDiary() {
        viewModelScope.launch {
            state.value = state.value.copy(data = repository.getAllData().toMutableStateList())
        }
    }
}

/**
 * Ui State
 */
data class UiState(
    val data: SnapshotStateList<DiaryEntity> = mutableStateListOf()
)

/**
 * Event事件
 */
sealed class DiaryListEvent {

}

/**
 * Intent
 */
sealed class DiaryListAction {
    object GetAllDiary : DiaryListAction()
}