package com.boristul.notebook.ui.settings.tags

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.boristul.notebook.R
import com.boristul.notebook.databinding.FragmentTagsBinding
import com.boristul.utils.distinctUntilChanged
import com.boristul.utils.hideKeyboard
import com.boristul.utils.setViewCount
import com.boristul.utils.viewbinding.viewBinding
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TagsFragment : Fragment(R.layout.fragment_tags) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel by viewModels<TagsFragmentViewModel>()
        val binding by viewBinding<FragmentTagsBinding>()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tags.collect { tags ->
                    binding.chips.setViewCount(
                        tags.size,
                        { layoutInflater.inflate(R.layout.item_tag_chip_edit, this, false) as Chip },
                        {
                            setOnCloseIconClickListener { _ ->
                                MaterialAlertDialogBuilder(requireContext())
                                    .setTitle(getString(R.string.tf_delete_title, tags[it].name))
                                    .setMessage(R.string.tf_delete_message)
                                    .setPositiveButton(R.string.tf_delete) { _, _ ->
                                        viewModel.delete(tags[it].id)
                                    }
                                    .setNegativeButton(R.string.tf_cancel, null)
                                    .show()
                            }
                            text = tags[it].name
                        }
                    )
                }
            }
        }

        binding.add.apply {
            viewModel.nameLiveData.observe(viewLifecycleOwner) { name ->
                isEnabled = name.isNotEmpty()
            }
            setOnClickListener {
                viewModel.addTag()
                binding.name.text?.clear()
            }
        }

        binding.name.apply {
            viewModel.nameLiveData.distinctUntilChanged { value ->
                value == text?.toString() ?: ""
            }.observe(viewLifecycleOwner) { setText(it) }
            addTextChangedListener { viewModel.nameLiveData.value = it?.toString() }
        }
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard()
    }
}
