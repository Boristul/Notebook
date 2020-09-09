package com.boristul.uikit

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.getSystemService
import com.boristul.uikit.databinding.CardNoteBinding
import com.google.android.material.card.MaterialCardView

class NoteCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.materialCardViewStyle
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val binding = CardNoteBinding.inflate(requireNotNull(context.getSystemService()), this)

    var onEditClickListener: (() -> Unit)? = null

    init {
        binding.edit.setOnClickListener { onEditClickListener?.invoke() }
    }

    fun expandCard(isAnimate: Boolean = true) = binding.apply {
        details.expand(isAnimate)
        edit.visibility = VISIBLE
    }

    fun toggleCard(isAnimate: Boolean = true) = binding.apply {
        details.toggle(isAnimate)
        edit.visibility = if (details.isExpanded) VISIBLE else GONE
    }

    fun collapseCard(isAnimate: Boolean = true) = binding.apply {
        details.collapse(isAnimate)
        edit.visibility = GONE
    }

    fun setExpanded(isExpanded: Boolean, isAnimate: Boolean) = binding.details.setExpanded(isExpanded, isAnimate)

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

    var text: CharSequence?
        get() = binding.text.text
        set(value) {
            binding.text.text = value
        }
}
