package com.boristul.notebook.ui.planner

import androidx.lifecycle.ViewModel
import com.boristul.entity.TaskPoint
import com.boristul.repository.TaskPointsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.joda.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PlannerFragmentViewModel @Inject constructor(
    private val taskPointRepository: TaskPointsRepository
) : ViewModel() {

    private val startDatePrivate = MutableStateFlow(LocalDate.now())
    val startDate: StateFlow<LocalDate> = startDatePrivate.asStateFlow()

    private val selectedDatePrivate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = selectedDatePrivate.asStateFlow()

    @ExperimentalCoroutinesApi
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
