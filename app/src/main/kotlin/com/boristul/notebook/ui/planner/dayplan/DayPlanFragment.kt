package com.boristul.notebook.ui.planner.dayplan

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentDayPlanBinding
import com.boristul.notebook.ui.planner.PlannerFragmentDirections
import com.boristul.notebook.ui.planner.taskadapter.TaskListAdapter
import com.boristul.utils.collectOnStarted
import com.boristul.utils.viewbinding.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.LocalDate

@AndroidEntryPoint
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
        @Suppress("UnsafeCast")
        viewModel.setSelectedDate(requireArguments().get(PAGE_DATE) as LocalDate)

        binding.tasksList.apply {
            adapter = TaskListAdapter().apply {
                val notEmptyIndex = 0
                val emptyIndex = 1

                viewModel.taskPoints.collectOnStarted(lifecycleScope, lifecycle) {
                    binding.viewSwitcher.displayedChild = if (it.isNotEmpty()) notEmptyIndex else emptyIndex
                    tasks = it
                }

                onClickListener = { task ->
                    if (task.isCompleted) {
                        MaterialAlertDialogBuilder(requireActivity())
                            .setTitle(R.string.pf_mark_uncompleted_title)
                            .setMessage(R.string.pf_mark_uncompleted_description)
                            .setPositiveButton(R.string.pf_yes) { _, _ -> viewModel.update(task.id, false) }
                            .setNegativeButton(R.string.pf_cancel, null)
                            .show()
                    } else {
                        viewModel.update(task.id, true)
                    }
                }

                onDeleteClickListener = { viewModel.delete(it.id) }

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
