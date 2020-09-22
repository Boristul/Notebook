package com.boristul.uikit

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.boristul.utils.getColorCompat

class StateProgressIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_COUNT = 4
    }

    var onStepChangedListener: ((item: Int) -> Unit)? = null

    var count: Int = DEFAULT_COUNT
        set(value) {
            field = value
            invalidate()
        }

    var stepsColor: ColorStateList? = ColorStateList.valueOf(context.getColorCompat(R.color.primary))
        set(value) {
            field = value
            invalidate()
        }

    var stepsDividerColor: ColorStateList? = ColorStateList.valueOf(context.getColorCompat(R.color.primary))
        set(value) {
            field = value
            invalidate()
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.StateProgressIndicator, defStyleAttr) {
            count = getInt(R.styleable.StateProgressIndicator_count, DEFAULT_COUNT)
            stepsColor = ColorStateList.valueOf(getColor(R.styleable.StateProgressIndicator_stepsColor, 0))
            stepsDividerColor = ColorStateList.valueOf(
                getColor(R.styleable.StateProgressIndicator_stepsDividerColor, 0)
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until count) {

        }
    }
}
