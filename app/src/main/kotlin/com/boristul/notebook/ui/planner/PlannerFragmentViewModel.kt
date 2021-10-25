package com.boristul.notebook.ui.planner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.boristul.entity.TaskPoint
import com.boristul.repository.TaskPointsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.joda.time.LocalDate
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class PlannerFragmentViewModel(application: Application) : AndroidViewModel(application), DIAware {
    override val di: DI by closestDI()
    private val taskPointRepository by instance<TaskPointsRepository>()

    private val startDatePrivate = MutableStateFlow(LocalDate.now())
    val startDate: StateFlow<LocalDate> = startDatePrivate.asStateFlow()

    private val selectedDatePrivate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = selectedDatePrivate.asStateFlow()

    private val taskPoints: Flow<List<TaskPoint>> = selectedDatePrivate.flatMapLatest {
        taskPointRepository.getTaskPoints(it)
    }

    val tasksCount: Flow<Pair<Int, Int>> = taskPoints.map { tasks ->
        Pair(
            tasks.count { it.isCompleted },
            tasks.size
        )
    }

    fun setStartDate(date: LocalDate) {
        startDatePrivate.value = date
    }

    fun setSelectedDate(date: LocalDate) {
        selectedDatePrivate.value = date
    }
}
