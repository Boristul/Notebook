package com.boristul.uikit

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.boristul.uikit.databinding.CardNoteBinding
import com.boristul.utils.attr
import com.daimajia.swipe.SwipeLayout

class NoteCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SwipeLayout(context, attrs, defStyleAttr) {
    private val binding = CardNoteBinding.inflate(requireNotNull(context.getSystemService()), this)

    var datetime: CharSequence?
        get() = binding.datetime.text
        set(value) {
            binding.datetime.text = value
        }

    var title: CharSequence?
        get() = binding.title.text
        set(value) {
            binding.title.text = value
        }

    var onDeleteClickListener: (() -> Unit)? = null

    init {
        binding.delete.setOnClickListener { onDeleteClickListener?.invoke() }

        context.apply {
            binding.surfaceLayout.background =
                ContextCompat.getDrawable(this, attr(android.R.attr.selectableItemBackground).resourceId)
        }
    }
}
