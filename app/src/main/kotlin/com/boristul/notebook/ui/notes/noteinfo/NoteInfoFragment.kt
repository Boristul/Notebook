package com.boristul.notebook.ui.notes.noteinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentNoteInfoBinding
import com.boristul.utils.navArgsFactory
import com.boristul.utils.viewbinding.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NoteInfoFragment : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_note_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel by viewModels<NoteInfoFragmentViewModel> {
            navArgsFactory<NoteInfoFragmentArgs> { application ->
                NoteInfoFragmentViewModel(application, note)
            }
        }
        val binding by viewBinding<FragmentNoteInfoBinding>()

        viewModel.note.run {
            binding.title.text = title
            binding.description.text = description
            binding.datetime.text = viewModel.getDateTimeString()
        }

        binding.edit.setOnClickListener {
            findNavController().navigate(NoteInfoFragmentDirections.actionNoteInfoToNoteEditor(viewModel.note))
        }
    }
}
