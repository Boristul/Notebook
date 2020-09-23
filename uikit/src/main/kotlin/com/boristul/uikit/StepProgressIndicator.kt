package com.boristul.uikit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.boristul.utils.getColorCompat

class StepProgressIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_COUNT = 4
    }

    // region Public

    var onStepChangedListener: ((item: Int) -> Unit)? = null

    var count: Int = DEFAULT_COUNT
        set(value) {
            field = value
            invalidate()
        }

    var stepsRadius: Float = 0f
        set(value) {
            field = value
            requestLayout()
        }

    var stepsColor: Int = context.getColorCompat(R.color.primary)
        set(value) {
            field = value
            invalidate()
        }

    var stepsDividerColor: Int = context.getColorCompat(R.color.primary)
        set(value) {
            field = value
            invalidate()
        }

    var selectedStep: Int = 0
        set(value) {
            field = value
            onStepChangedListener?.invoke(field)
            invalidate()
        }

    // endregion

    init {
        context.withStyledAttributes(attrs, R.styleable.StepProgressIndicator, defStyleAttr) {
            count = getInt(R.styleable.StepProgressIndicator_count, DEFAULT_COUNT)
            stepsRadius = getDimension(R.styleable.StepProgressIndicator_stepRadius, 40f)
            selectedStep = getInt(R.styleable.StepProgressIndicator_selectedStep, DEFAULT_COUNT)
            stepsColor = getColor(R.styleable.StepProgressIndicator_stepsColor, 0)
            stepsDividerColor = getColor(R.styleable.StepProgressIndicator_stepsDividerColor, 0)

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (stepsRadius * 2).toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val lineSize = lineWidth

        for (i in 0 until count) {
            canvas?.drawCircle(calculateXPositionForStep(i), yPosition, stepsRadius, stepsPaint)
        }
    }

    // region private

    private val stepsPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    private val stepsLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    private val stepsStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLUE
    }

    private val yPosition
        get() = stepsRadius / 2

    private val lineWidth: Float
        get() = (width - (stepsRadius * count)) / (count - 1)


    private fun calculateXPositionForStep(stepIndex: Int): Float =
        (stepsRadius + lineWidth) * stepIndex + stepsRadius / 2

    // endregion
}
