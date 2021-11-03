package com.boristul.notebook.ui.notes

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentNotesBinding

import com.boristul.utils.collectOnStarted
import com.boristul.utils.getColorCompat
import com.boristul.utils.setColor
import com.boristul.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentNotesBinding.bind(view)
        val viewModel by viewModels<NotesFragmentViewModel>()

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHasOptionsMenu(true)
        }

        binding.notesList.apply {
            adapter = NotesListAdapter().apply {
                val notEmptyIndex = 0
                val emptyIndex = 1

                viewModel.notes.collectOnStarted(lifecycleScope, lifecycle) {
                    binding.viewSwitcher.displayedChild = if (it.isNotEmpty()) notEmptyIndex else emptyIndex
                    notes = it
                }

                onDeleteClickListener = {
                    viewModel.delete(it.note.id)
                }

                onClickListener = {
                    findNavController().navigate(NotesFragmentDirections.actionNotesToNoteInfo(it))
                }
            }
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }

        binding.addNote.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.actionNotesToNoteEditor(null))
        }

        viewModel.state.collectOnStarted(lifecycleScope, lifecycle) { state ->
            when (state) {
                NoteState.Started -> Unit
                is NoteState.NoteDeleted -> requireActivity().toast(R.string.nf_successful_delete)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_notes, menu)
        menu.setColor(requireContext().getColorCompat(R.color.menu))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.mn_add -> {
            findNavController().navigate(NotesFragmentDirections.actionNotesToNoteEditor(null))
            true
        }
        else -> false
    }
}
