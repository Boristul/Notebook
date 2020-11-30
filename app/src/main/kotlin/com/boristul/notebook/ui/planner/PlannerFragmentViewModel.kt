package com.boristul.notebook.ui.planner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.boristul.entity.TaskPoint
import com.boristul.repository.TaskPointRepository
import org.joda.time.LocalDate
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class PlannerFragmentViewModel(application: Application) : AndroidViewModel(application), DIAware {
    override val di: DI by di()
    private val taskPointRepository by instance<TaskPointRepository>()

    val selectedDate = MutableLiveData(LocalDate.now())

    val taskPoints: LiveData<List<TaskPoint>> = selectedDate.switchMap {
        taskPointRepository.getTaskPoints(it)
    }

    suspend fun addTask(text: String) = taskPointRepository.insert(text, checkNotNull(selectedDate.value))

    suspend fun delete(id: Long) = taskPointRepository.delete(id)
}
