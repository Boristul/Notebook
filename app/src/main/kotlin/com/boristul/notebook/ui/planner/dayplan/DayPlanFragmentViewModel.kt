package com.boristul.notebook.ui.planner.dayplan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.boristul.entity.TaskPoint
import com.boristul.repository.TaskPointsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class DayPlanFragmentViewModel(application: Application) : AndroidViewModel(application), DIAware {
    override val di: DI by closestDI()
    private val taskPointRepository by instance<TaskPointsRepository>()

    private val selectedDatePrivate = MutableStateFlow(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = selectedDatePrivate.asStateFlow()

    @ExperimentalCoroutinesApi
    val taskPoints: Flow<List<TaskPoint>> = selectedDate.flatMapLatest {
        taskPointRepository.getTaskPoints(it)
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            taskPointRepository.delete(id)
        }
    }

    fun update(id: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            taskPointRepository.update(id, isCompleted)
        }
    }

    fun setSelectedDate(date: LocalDate) {
        selectedDatePrivate.value = date
    }
}
