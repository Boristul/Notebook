package com.boristul.notebook.ui.notes

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentNotesBinding
import com.boristul.utils.getColorCompat
import com.boristul.utils.setColor
import com.boristul.utils.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class NotesFragment : Fragment(R.layout.fragment_notes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel by viewModels<NotesFragmentViewModel>()
        val binding = FragmentNotesBinding.bind(view)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHasOptionsMenu(true)
        }

        binding.notesList.apply {
            adapter = NotesListAdapter().apply {
                val notEmptyIndex = 0
                val emptyIndex = 1

                viewModel.notes.observe(viewLifecycleOwner) {
                    binding.viewSwitcher.displayedChild = if (it.isNotEmpty()) notEmptyIndex else emptyIndex
                    notes = it
                }
                onDeleteClickListener = {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.nf_delete_title)
                        .setPositiveButton(R.string.nf_delete) { _, _ ->
                            viewModel.viewModelScope.launch {
                                viewModel.delete(it.note.id)
                                requireActivity().toast(R.string.nf_successful_delete)
                            }
                        }
                        .setNegativeButton(R.string.nf_cancel, null)
                        .show()
                }

                onClickListener = {
                    findNavController().navigate(NotesFragmentDirections.actionNotesToNoteInfo(it))
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
            findNavController().navigate(NotesFragmentDirections.actionNotesToNoteEditor(null))
            true
        }
        else -> false
    }
}
