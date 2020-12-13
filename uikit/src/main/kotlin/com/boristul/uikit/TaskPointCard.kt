package com.boristul.uikit

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.getSystemService
import com.boristul.uikit.databinding.CardTaskPointBinding
import com.boristul.utils.attr
import com.boristul.utils.getDrawableCompat
import com.daimajia.swipe.SwipeLayout

class TaskPointCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SwipeLayout(context, attrs, defStyleAttr) {

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

    var onDeleteClickListener: (() -> Unit)? = null
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
        binding.delete.setOnClickListener { onDeleteClickListener?.invoke() }
        surfaceView.setOnClickListener { onClickListener?.invoke() }
        surfaceView.setOnLongClickListener {
            onLongClickListener?.invoke()
            true
        }

        context.apply {
            binding.surfaceLayout.background =
                getDrawableCompat(attr(android.R.attr.selectableItemBackground).resourceId)
        }
    }
}
