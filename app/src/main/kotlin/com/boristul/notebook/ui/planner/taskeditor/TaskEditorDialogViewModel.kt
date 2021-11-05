package com.boristul.notebook.ui.planner.taskeditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.boristul.entity.TaskPoint
import com.boristul.repository.TaskPointsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.joda.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TaskEditorDialogViewModel @Inject constructor(
    private val taskPointsRepository: TaskPointsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val task = savedStateHandle.get<TaskPoint>("task")
    private val date = savedStateHandle.get<LocalDate>("date")

    var isEdition: Boolean = false
        private set

    val title = MutableLiveData("")
    val isTitleNotBlank: LiveData<Boolean> = title.map { !it.isNullOrBlank() }

    init {
        task?.let {
            require(date == null)
            isEdition = true
            title.value = task.title
        }
    }

    suspend fun save() {
        if (!isEdition) {
            taskPointsRepository.insert(checkNotNull(title.value), checkNotNull(date))
        } else {
            taskPointsRepository.update(checkNotNull(task).id, checkNotNull(title.value))
        }
    }
}
