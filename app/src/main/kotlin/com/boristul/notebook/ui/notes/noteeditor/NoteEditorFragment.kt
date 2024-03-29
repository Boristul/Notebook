package com.boristul.notebook.ui.notes.noteeditor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.navigation.fragment.findNavController
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentNoteEditorBinding
import com.boristul.utils.collectOnStarted
import com.boristul.utils.distinctUntilChanged
import com.boristul.utils.hideKeyboard
import com.boristul.utils.setViewCount
import com.boristul.utils.toast
import com.boristul.utils.viewbinding.viewBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteEditorFragment : Fragment(R.layout.fragment_note_editor) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding by viewBinding<FragmentNoteEditorBinding>()
        val viewModel by viewModels<NoteEditorFragmentViewModel>()

        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(
            if (viewModel.isEdition) R.string.nef_title_edit else R.string.nef_title_create
        )

        binding.title.apply {
            viewModel.title.distinctUntilChanged { value ->
                value == text?.toString() ?: ""
            }.observe(viewLifecycleOwner) { setText(it) }
            addTextChangedListener { viewModel.title.value = it?.toString() }
        }

        binding.description.apply {
            viewModel.description.distinctUntilChanged { value ->
                value == text?.toString() ?: ""
            }.observe(viewLifecycleOwner) { setText(it) }
            addTextChangedListener { viewModel.description.value = it?.toString() }
        }

        binding.save.apply {
            viewModel.isDataNotEmpty.distinctUntilChanged().observe(viewLifecycleOwner) {
                isEnabled = it
            }

            setOnClickListener {
                viewModel.save()
            }
        }

        viewModel.state.collectOnStarted(viewLifecycleOwner) { state ->
            when (state) {
                NoteEditorState.Started -> Unit
                NoteEditorState.SaveCompleted -> {
                    requireActivity().toast(R.string.nef_successful_save)
                    findNavController().popBackStack()
                }
                NoteEditorState.Loading -> Unit
            }
        }

        viewModel.tags.collectOnStarted(viewLifecycleOwner) { tags ->
            binding.chips.setViewCount(
                tags.size,
                { layoutInflater.inflate(R.layout.item_tag_chip_choice, this, false) as Chip },
                {
                    text = tags[it].name
                    isChecked = viewModel.isTagSelected(tags[it])

                    setOnCheckedChangeListener { _, isChecked ->
                        viewModel.updateTagsList(tags[it], isChecked)
                    }
                }
            )
        }
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }
}
