package com.boristul.notebook.ui.planner.taskeditor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.boristul.entity.TaskPoint
import com.boristul.repository.TaskPointsRepository
import org.joda.time.LocalDate
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class TaskEditorDialogViewModel(
    application: Application,
    private val task: TaskPoint?,
    private val date: LocalDate?
) : AndroidViewModel(application), DIAware {
    override val di: DI by di()
    private val taskPointRepository by instance<TaskPointsRepository>()

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
            taskPointRepository.insert(checkNotNull(title.value), checkNotNull(date))
        } else {
            taskPointRepository.update(checkNotNull(task).id, checkNotNull(title.value))
        }
    }
}
