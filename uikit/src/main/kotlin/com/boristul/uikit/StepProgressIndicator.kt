package com.boristul.uikit

import android.content.Context
import android.graphics.Canvas
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
            stepsColor = getColor(R.styleable.StepProgressIndicator_stepsColor, stepsColor)
            stepsDividerColor = getColor(R.styleable.StepProgressIndicator_stepsDividerColor, stepsDividerColor)

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (stepsRadius * 2).toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val lineWidth = lineWidth

        for (i in 0 until count) {
            val xPosition = getStepXPosition(i)
            drawUnfulfilledStep(canvas, xPosition, yPosition)

            if (i < count)
                drawLine(canvas, xPosition + stepsRadius, yPosition, lineWidth)
        }
    }

    // region private

    private fun drawCompletedStep(canvas: Canvas?, xPosition: Float, yPosition: Float) {
        canvas?.drawCircle(xPosition, yPosition, stepsRadius, stepsPaint)
    }

    private fun drawUnfulfilledStep(canvas: Canvas?, xPosition: Float, yPosition: Float) {
        canvas?.drawCircle(xPosition, yPosition, stepsRadius / 4, stepsPaint)
        canvas?.drawCircle(xPosition, yPosition, stepsRadius, stepsStrokePaint)
    }

    private fun drawLine(canvas: Canvas?, startXPosition: Float, yPosition: Float, lineWidth: Float) {
        canvas?.drawLine(startXPosition, yPosition, startXPosition + lineWidth, yPosition, stepsLinePaint)
    }

    private val stepsPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = stepsColor
    }

    private val stepsLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = stepsColor
    }

    private val stepsStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = stepsColor
    }

    private val yPosition
        get() = stepsRadius

    private val lineWidth: Float
        get() = (width - (stepsRadius * 2 * count)) / (count - 1)


    private fun getStepXPosition(stepIndex: Int): Float =
        (stepsRadius * 2 + lineWidth) * stepIndex + stepsRadius

    // endregion
}
