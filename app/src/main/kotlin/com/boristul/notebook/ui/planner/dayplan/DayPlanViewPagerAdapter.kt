package com.boristul.notebook.ui.planner.dayplan

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.joda.time.LocalDate

class DayPlanViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var startDate: LocalDate = LocalDate.now()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = startDate.dayOfMonth().withMaximumValue().dayOfMonth

    override fun createFragment(position: Int): Fragment =
        DayPlanFragment.getInstance(startDate.withDayOfMonth(1).plusDays(position))
}
