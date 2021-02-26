package com.boristul.notebook.ui.planner

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentPlannerBinding
import com.boristul.notebook.ui.planner.dayplan.DayPlanViewPagerAdapter
import com.boristul.utils.getColorCompat
import com.boristul.utils.setColor
import com.boristul.utils.showDatePicker
import com.boristul.utils.viewbinding.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import org.joda.time.DateTimeZone
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat

class PlannerFragment : Fragment(R.layout.fragment_planner) {

    private val viewModel by viewModels<PlannerFragmentViewModel>()
    private val binding by viewBinding<FragmentPlannerBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val owner = viewLifecycleOwner
        val dayPattern = DateTimeFormat.forPattern("dd.MM.YYYY").withZone(DateTimeZone.getDefault())

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHasOptionsMenu(true)

            viewModel.tasksCount.observe(owner) {
                title = getString(R.string.pf_completed_count, it.first.toString(), it.second.toString())
            }
        }

        binding.viewPager.apply {
            adapter = DayPlanViewPagerAdapter(this@PlannerFragment).apply {
                viewModel.startDate.observe(owner) {
                    viewModel.selectedDate.value = it
                    startDate = it
                    setCurrentItem(it.dayOfMonth - 1, false)
                }
            }

            TabLayoutMediator(binding.tabLayout, this) { tab, position ->
                tab.text =
                    checkNotNull(viewModel.selectedDate.value).withDayOfMonth(1).plusDays(position).toString(dayPattern)
            }.attach()

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.selectedDate.value =
                        checkNotNull(viewModel.selectedDate.value).withDayOfMonth(1).plusDays(position)
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_planner, menu)
        menu.setColor(requireContext().getColorCompat(R.color.menu))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.mp_add -> {
            findNavController().navigate(
                PlannerFragmentDirections.actionPlannerToTaskEditor(date = checkNotNull(viewModel.selectedDate.value))
            )
            true
        }
        R.id.mp_calendar -> {
            showCalendarDialog()
            true
        }
        else -> false
    }

    private fun showCalendarDialog() {
        requireContext().showDatePicker(
            viewModel.selectedDate.value,
            LocalDate.now().minusYears(1),
            LocalDate.now().plusYears(1)
        ) {
            viewModel.startDate.value = it
        }
    }
}
