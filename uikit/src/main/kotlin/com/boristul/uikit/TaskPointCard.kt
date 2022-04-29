package com.boristul.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import com.boristul.uikit.databinding.CardTaskPointBinding
import com.boristul.utils.attr
import com.boristul.utils.getDrawableCompat

class TaskPointCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val CHECKED_SPEED = 2f
        private const val UNCHECKED_SPEED = -2f
    }

    private val binding = CardTaskPointBinding.inflate(requireNotNull(context.getSystemService()), this)

    var title: CharSequence?
        get() = binding.title.text
        set(value) {
            binding.title.text = value
        }

    var onClickListener: (() -> Unit)? = null
    var onLongClickListener: (() -> Unit)? = null

    fun setChecked(isChecked: Boolean, isAnimated: Boolean = true) {
        binding.checkbox.apply {
            when {
                isAnimated && isChecked -> {
                    speed = CHECKED_SPEED
                    playAnimation()
                }
                isAnimated && !isChecked -> {
                    speed = UNCHECKED_SPEED
                    playAnimation()
                }
                else -> binding.checkbox.progress = if (isChecked) 1f else 0f
            }
        }
    }

    init {
        setOnClickListener { onClickListener?.invoke() }
        setOnLongClickListener {
            onLongClickListener?.invoke()
            true
        }

        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val verticalPadding = context.resources.getDimensionPixelSize(R.dimen.card_vertical_margin)
        val horizontalPadding = context.resources.getDimensionPixelSize(R.dimen.card_horizontal_margin)

        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)

        context.apply {
            background =
                getDrawableCompat(attr(android.R.attr.selectableItemBackground).resourceId)
        }
    }
}
