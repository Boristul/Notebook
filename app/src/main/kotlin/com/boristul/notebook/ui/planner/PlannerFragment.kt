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
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentPlannerBinding
import com.boristul.utils.getColorCompat
import com.boristul.utils.setColor
import com.boristul.utils.toast
import com.boristul.utils.viewbinding.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import java.util.Calendar

class PlannerFragment : Fragment(R.layout.fragment_planner) {
    private val viewModel by viewModels<PlannerFragmentViewModel>()
    private val binding by viewBinding<FragmentPlannerBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHasOptionsMenu(true)
        }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_main, menu)
        menu.setColor(requireContext().getColorCompat(R.color.menu))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.nav_action_add -> {
            MaterialAlertDialogBuilder(requireActivity())
                .setView(R.layout.item_task_editor)
                .setTitle(R.string.pf_add_task_title)
                .setPositiveButton(R.string.pf_create) { dialog, _ ->
                    // TODO: Think about how to get text using View Binding
                    val text = (dialog as AlertDialog).requireViewById<TextInputEditText>(R.id.md_input_message).text.toString()
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
        else -> false
    }
}
