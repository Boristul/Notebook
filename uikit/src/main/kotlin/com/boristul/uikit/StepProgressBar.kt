package com.boristul.uikit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.boristul.utils.getColorCompat

class StepProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_COUNT = 4
        private const val DEFAULT_STEPS_RADIUS = 40f
    }

    // region public

    var onStepChangedListener: ((item: Int) -> Unit)? = null

    var count: Int = DEFAULT_COUNT
        set(value) {
            field = value
            invalidate()
        }

    var stepsRadius: Float = DEFAULT_STEPS_RADIUS
        set(value) {
            field = value
            requestLayout()
        }

    var completedStepsColor: Int = context.getColorCompat(R.color.primary)
        set(value) {
            field = value
            invalidate()
        }

    var unfulfilledStepColor: Int = context.getColorCompat(R.color.on_background)
        set(value) {
            field = value
            invalidate()
        }

    var selectedStep: Int = 0
        set(value) {
            require(value < count) { "Selected step can't be more than the number of steps" }
            field = value
            onStepChangedListener?.invoke(field)
            invalidate()
        }

    // endregion

    init {
        context.withStyledAttributes(attrs, R.styleable.StepProgressBar, defStyleAttr) {
            count = getInt(R.styleable.StepProgressBar_count, DEFAULT_COUNT)
            stepsRadius = getDimension(R.styleable.StepProgressBar_stepsRadius, DEFAULT_STEPS_RADIUS)
            selectedStep = getInt(R.styleable.StepProgressBar_selectedStep, 0)
            completedStepsColor = getColor(R.styleable.StepProgressBar_stepsColor, completedStepsColor)
            unfulfilledStepColor = getColor(R.styleable.StepProgressBar_stepsDividerColor, unfulfilledStepColor)

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (stepsRadius * 2).toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val lineWidth = lineWidth
        setPaintsColor(completedStepsColor)

        for (i in 0 until count) {
            val xPosition = getStepXPosition(i)

            if (selectedStep > i)
                drawCompletedStep(canvas, xPosition, yPosition)
            else
                drawUnfulfilledStep(canvas, xPosition, yPosition)

            if (i == selectedStep)
                setPaintsColor(unfulfilledStepColor)

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

    private fun setPaintsColor(color: Int) {
        stepsPaint.color = color
        stepsLinePaint.color = color
        stepsStrokePaint.color = color
    }

    private val stepsPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = completedStepsColor
    }

    private val stepsLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = completedStepsColor
    }

    private val stepsStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = completedStepsColor
    }

    private val yPosition
        get() = stepsRadius

    private val lineWidth: Float
        get() = (width - (stepsRadius * 2 * count)) / (count - 1)

    private fun getStepXPosition(stepIndex: Int): Float =
        (stepsRadius * 2 + lineWidth) * stepIndex + stepsRadius

    // endregion
}
