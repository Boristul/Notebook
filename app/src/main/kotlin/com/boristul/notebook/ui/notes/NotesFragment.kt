package com.boristul.notebook.ui.notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentNotesBinding

class NotesFragment : Fragment(R.layout.fragment_notes) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel by viewModels<NotesFragmentViewModel>()
        val binding = FragmentNotesBinding.bind(view)

        binding.notesList.apply {
            adapter = NotesListAdapter().apply {
                viewModel.notes.observe(viewLifecycleOwner) {
                    notes = it
                }
            }
        }
    }
}
