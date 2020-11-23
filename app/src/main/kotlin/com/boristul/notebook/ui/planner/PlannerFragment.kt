package com.boristul.notebook.ui.planner

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentPlannerBinding
import com.boristul.utils.toast
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import java.util.Calendar

class PlannerFragment : Fragment(R.layout.fragment_planner) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentPlannerBinding.bind(view)

        val calendar = HorizontalCalendar.Builder(view, binding.calendar.id)
            .range(
                Calendar.getInstance().apply { add(Calendar.MONTH, -1) },
                Calendar.getInstance().apply { add(Calendar.MONTH, 1) }
            )
            .datesNumberOnScreen(5)
            .build()

        calendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar?, position: Int) {
                requireActivity().toast(date.toString())
            }
        }
    }
}
