package com.boristul.notebook.ui.planner.taskeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.boristul.notebook.databinding.DialogTaskEditorBinding
import com.boristul.utils.viewbinding.inflateViewBinding
import com.boristul.utils.viewbinding.viewBinding

class TaskEditorDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflateViewBinding<DialogTaskEditorBinding>(container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding by viewBinding<DialogTaskEditorBinding>()
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
