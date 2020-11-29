package com.boristul.notebook.ui.planner

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentPlannerBinding
import com.boristul.utils.toast
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import java.util.Calendar

class PlannerFragment : Fragment(R.layout.fragment_planner) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentPlannerBinding.bind(view)
        val viewModel by viewModels<PlannerFragmentViewModel>()

        val calendar = HorizontalCalendar.Builder(view, binding.calendar.id)
            .range(
                Calendar.getInstance().apply { add(Calendar.MONTH, -1) },
                Calendar.getInstance().apply { add(Calendar.MONTH, 1) }
            )
            .datesNumberOnScreen(5)
            .build()

        calendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                viewModel.selectedDate.value = LocalDate(date)
            }
        }

        binding.tasksList.apply {
            adapter = TaskListAdapter().apply {
                viewModel.taskPoints.observe(viewLifecycleOwner) {
                    tasks = it
                }

                onDeleteClickListener = {
                    viewModel.viewModelScope.launch {
                        viewModel.delete(it.id)
                        requireActivity().toast(R.string.pf_successful_delete)
                    }
                }
            }
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }
}
