package com.boristul.notebook.ui.planner.dayplan

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentDayPlanBinding
import com.boristul.notebook.ui.planner.PlannerFragmentDirections
import com.boristul.notebook.ui.planner.taskadapter.TaskListAdapter
import com.boristul.utils.viewbinding.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.joda.time.LocalDate

class DayPlanFragment : Fragment(R.layout.fragment_day_plan) {

    companion object {
        private const val PAGE_DATE = "page_date"

        fun getInstance(date: LocalDate) = DayPlanFragment().apply {
            arguments = bundleOf(PAGE_DATE to date)
        }
    }

    private val binding by viewBinding<FragmentDayPlanBinding>()
    private val viewModel by viewModels<DayPlanFragmentViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val owner = viewLifecycleOwner

        @Suppress("UnsafeCast")
        viewModel.selectedDate.value = requireArguments().get(PAGE_DATE) as LocalDate

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
                onLongClickListener = {
                    findNavController().navigate(PlannerFragmentDirections.actionPlannerToTaskEditor(task = it))
                }
            }
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
        binding.addTask.setOnClickListener {
            findNavController().navigate(
                PlannerFragmentDirections.actionPlannerToTaskEditor(date = checkNotNull(viewModel.selectedDate.value))
            )
        }
    }
}
