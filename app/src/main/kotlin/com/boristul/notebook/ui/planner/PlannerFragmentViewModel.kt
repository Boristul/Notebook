package com.boristul.notebook.ui.planner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.boristul.entity.TaskPoint
import com.boristul.repository.TaskPointsRepository
import org.joda.time.LocalDate
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class PlannerFragmentViewModel(application: Application) : AndroidViewModel(application), DIAware {
    override val di: DI by di()
    private val taskPointRepository by instance<TaskPointsRepository>()

    val startDate = MutableLiveData(LocalDate.now())
    val selectedDate = MutableLiveData(LocalDate.now())

    private val taskPoints: LiveData<List<TaskPoint>> = selectedDate.switchMap {
        taskPointRepository.getTaskPoints(it)
    }

    val tasksCount: LiveData<Pair<Int, Int>> = taskPoints.map { tasks ->
        Pair(
            tasks.count { it.isCompleted },
            tasks.size
        )
    }
}
