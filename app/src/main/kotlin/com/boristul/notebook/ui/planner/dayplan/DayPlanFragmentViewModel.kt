package com.boristul.notebook.ui.planner.dayplan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.boristul.entity.TaskPoint
import com.boristul.repository.TaskPointsRepository
import org.joda.time.LocalDate
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class DayPlanFragmentViewModel(application: Application) : AndroidViewModel(application), DIAware {
    override val di: DI by di()
    private val taskPointRepository by instance<TaskPointsRepository>()

    val selectedDate = MutableLiveData(LocalDate.now())

    val taskPoints: LiveData<List<TaskPoint>> = selectedDate.switchMap {
        taskPointRepository.getTaskPoints(it)
    }

    suspend fun delete(id: Long) = taskPointRepository.delete(id)
    suspend fun update(id: Long, isCompleted: Boolean) = taskPointRepository.update(id, isCompleted)
}
