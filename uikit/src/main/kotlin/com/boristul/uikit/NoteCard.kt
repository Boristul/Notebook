package com.boristul.uikit

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.boristul.uikit.databinding.CardNoteBinding
import com.boristul.utils.attr

class NoteCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = CardNoteBinding.inflate(requireNotNull(context.getSystemService()), this)

    var imageTintList: ColorStateList?
        get() = binding.statusIcon.imageTintList
        set(value) {
            binding.statusIcon.imageTintList = value
        }

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

    init {
        context.apply {
            val cardPadding = resources.getDimensionPixelSize(R.dimen.cards_spacing)
            setPaddingRelative(
                cardPadding,
                cardPadding,
                cardPadding,
                cardPadding
            )
            background = ContextCompat.getDrawable(this, attr(android.R.attr.selectableItemBackground).resourceId)
        }
    }
}
