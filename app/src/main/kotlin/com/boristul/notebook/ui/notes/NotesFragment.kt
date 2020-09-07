package com.boristul.notebook.ui.notes

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentNotesBinding

class NotesFragment : Fragment(R.layout.fragment_notes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel by viewModels<NotesFragmentViewModel>()
        val binding = FragmentNotesBinding.bind(view)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHasOptionsMenu(true)
        }

        binding.notesList.apply {
            adapter = NotesListAdapter().apply {
                viewModel.notes.observe(viewLifecycleOwner) {
                    notes = it
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.top_main, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.nav_action_add -> {
            true
        }
        else -> false
    }
}
