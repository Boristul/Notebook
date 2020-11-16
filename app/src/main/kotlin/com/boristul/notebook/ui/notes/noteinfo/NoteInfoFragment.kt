package com.boristul.notebook.ui.notes.noteinfo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentNoteInfoBinding
import com.boristul.utils.navArgsFactory
import com.boristul.utils.setViewCount
import com.boristul.utils.viewbinding.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

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

        viewModel.noteWithTags.note.run {
            binding.title.let {
                if (title.isNotEmpty()) {
                    it.visibility = View.VISIBLE
                    it.text = title
                }
            }
            binding.description.let {
                if (description.isNotEmpty()) {
                    it.visibility = View.VISIBLE
                    it.text = description
                }
            }
            binding.datetime.text = viewModel.getDateTimeString()
        }

        binding.edit.setOnClickListener {
            findNavController().navigate(NoteInfoFragmentDirections.actionNoteInfoToNoteEditor(viewModel.noteWithTags))
        }

        viewModel.noteWithTags.tags.let { tags ->
            if (tags.isNotEmpty()) {
                binding.chips.visibility = View.VISIBLE
                binding.tagsTitle.visibility = View.VISIBLE

                binding.chips.setViewCount(
                    tags.size,
                    { layoutInflater.inflate(R.layout.item_tag_chip_choice, this, false) as Chip },
                    {
                        text = tags[it].name
                        isClickable = false
                    }
                )
            }
        }

        binding.share.setOnClickListener {
            startActivity(
                Intent.createChooser(
                    Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, viewModel.getNoteForShare())
                        type = "text/plain"
                    },
                    getString(R.string.app_name)
                )
            )
        }
    }
}
