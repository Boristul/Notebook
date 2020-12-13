package com.boristul.notebook.ui.planner

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentPlannerBinding
import com.boristul.notebook.ui.planner.taskadapter.TaskListAdapter
import com.boristul.utils.getColorCompat
import com.boristul.utils.setColor
import com.boristul.utils.showDatePicker
import com.boristul.utils.viewbinding.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import java.util.Calendar

class PlannerFragment : Fragment(R.layout.fragment_planner) {

    companion object {
        private const val COUNT_DATES_ON_SCREEN = 5
    }

    private val viewModel by viewModels<PlannerFragmentViewModel>()
    private val binding by viewBinding<FragmentPlannerBinding>()

    private val calendar: HorizontalCalendar by lazy {
        HorizontalCalendar.Builder(view, binding.calendar.id)
            .range(
                Calendar.getInstance().apply { add(Calendar.YEAR, -1) },
                Calendar.getInstance().apply { add(Calendar.YEAR, 1) }
            )
            .datesNumberOnScreen(COUNT_DATES_ON_SCREEN)
            .build()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val owner = viewLifecycleOwner

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHasOptionsMenu(true)

            viewModel.tasksCount.observe(owner) {
                title = getString(R.string.pf_completed_count, it.first.toString(), it.second.toString())
            }
        }

        calendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                viewModel.selectedDate.value = LocalDate(date)
            }
        }

        binding.tasksList.apply {
            adapter = TaskListAdapter().apply {
                val notEmptyIndex = 0
                val emptyIndex = 1

                viewModel.taskPoints.distinctUntilChanged().observe(owner) {
                    binding.viewSwitcher.displayedChild = if (it.isNotEmpty()) notEmptyIndex else emptyIndex
                    tasks = it
                }

                onClickListener = { task ->
                    if (task.isCompleted) {
                        MaterialAlertDialogBuilder(requireActivity())
                            .setTitle(R.string.pf_mark_uncompleted_title)
                            .setMessage(R.string.pf_mark_uncompleted_description)
                            .setPositiveButton(R.string.pf_yes) { _, _ ->
                                viewModel.viewModelScope.launch {
                                    viewModel.update(task.id, !task.isCompleted)
                                }
                            }
                            .setNegativeButton(R.string.pf_cancel, null)
                            .show()
                    } else {
                        viewModel.viewModelScope.launch {
                            viewModel.update(task.id, !task.isCompleted)
                        }
                    }
                }

                onDeleteClickListener = {
                    viewModel.viewModelScope.launch {
                        viewModel.delete(it.id)
                    }
                }
                onLongClickListener = { findNavController().navigate(R.id.action_planner_to_task_editor) }
            }
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_planner, menu)
        menu.setColor(requireContext().getColorCompat(R.color.menu))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.mp_add -> {
            MaterialAlertDialogBuilder(requireActivity())
                .setView(R.layout.dialog_task_editor)
                .setTitle(R.string.pf_add_task_title)
                .setPositiveButton(R.string.pf_create) { dialog, _ ->
                    // TODO: Think about how to get text using View Binding
                    val text = (dialog as AlertDialog).requireViewById<TextInputEditText>(R.id.tei_input_message).text.toString()
                    viewModel.apply {
                        viewModelScope.launch {
                            addTask(text)
                        }
                    }
                }
                .setNegativeButton(R.string.pf_cancel, null)
                .show()
            true
        }
        R.id.mp_calendar -> {
            requireContext().showDatePicker(
                viewModel.selectedDate.value,
                LocalDate.now().minusYears(1),
                LocalDate.now().plusYears(1)
            ) {
                calendar.selectDate(
                    Calendar.getInstance().apply {
                        time = it.toDate()
                    },
                    true
                )
            }
            true
        }
        else -> false
    }
}
