package com.boristul.notebook.ui.planner.taskeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.boristul.notebook.R
import com.boristul.notebook.databinding.DialogTaskEditorBinding
import com.boristul.notebook.ui.notes.noteeditor.NoteEditorFragmentArgs
import com.boristul.notebook.ui.notes.noteeditor.NoteEditorFragmentViewModel
import com.boristul.utils.distinctUntilChanged
import com.boristul.utils.navArgsFactory
import com.boristul.utils.viewbinding.inflateViewBinding
import com.boristul.utils.viewbinding.viewBinding
import kotlinx.coroutines.launch

class TaskEditorDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflateViewBinding<DialogTaskEditorBinding>(container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding by viewBinding<DialogTaskEditorBinding>()
        val owner = viewLifecycleOwner

        val viewModel by viewModels<TaskEditorDialogViewModel> {
            navArgsFactory<TaskEditorDialogArgs> { application ->
                TaskEditorDialogViewModel(application, task, date)
            }
        }

        binding.title.text = getString(
            if (viewModel.isEdition) R.string.ted_edit_title else R.string.ted_create_title
        )

        binding.taskTitle.apply {
            viewModel.title.distinctUntilChanged { value ->
                value == text?.toString() ?: ""
            }.observe(owner) { setText(it) }
            addTextChangedListener { viewModel.title.value = it?.toString() }
        }

        binding.save.apply {
            viewModel.isTitleNotBlank.distinctUntilChanged().observe(owner) {
                isEnabled = it
            }

            setOnClickListener {
                viewModel.viewModelScope.launch {
                    viewModel.save()
                    findNavController().popBackStack()
                }
            }
        }

        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
