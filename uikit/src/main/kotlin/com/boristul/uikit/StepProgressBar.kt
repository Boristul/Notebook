package com.boristul.uikit

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
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
        private const val DEFAULT_TEXT_SIZE = 30f

        // TODO: create param in xml and code
        private const val TEXT_PADDING = 20f
    }

    var onStepChangedListener: ((item: Int) -> Unit)? = null

    var count: Int = DEFAULT_COUNT
        set(value) {
            field = value
            invalidate()
        }

    @Dimension
    var stepsRadius: Float = DEFAULT_STEPS_RADIUS
        set(value) {
            field = value
            requestLayout()
        }

    @Dimension
    var titleTextSize: Float = DEFAULT_TEXT_SIZE
        set(value) {
            field = value
            requestLayout()
        }

    @ColorInt
    var completedStepsColor: Int = context.getColorCompat(R.color.primary)
        set(value) {
            field = value
            invalidate()
        }

    @ColorInt
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

    private val yPosition
        get() = stepsRadius

    private val lineWidth: Float
        get() = (width - (stepsRadius * 2 * count)) / (count - 1)

    private val stepsPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val stepsLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val stepsStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val stepsTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = titleTextSize
    }

    private var titlesLayouts: List<StaticLayout> = listOf()

    var stepsTitles: List<String> = listOf()
        set(value) {
            field = value
            requestLayout()
        }

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
        stepsTextPaint.color = color
    }

    private fun getStepXPosition(stepIndex: Int): Float =
        (stepsRadius * 2 + lineWidth) * stepIndex + stepsRadius

    private fun getViewHeight(): Float = 2 * stepsRadius + getStepsTitlesHeight() + paddingTop + paddingBottom + TEXT_PADDING

    private fun getStepsTitlesHeight(): Int = titlesLayouts.map { it.height }.max() ?: 0

    private fun getTitlesLayouts() = stepsTitles.map { text ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(
                text,
                0,
                text.length,
                stepsTextPaint,
                measuredWidth / stepsTitles.size
            ).build()
        } else {
            StaticLayout(
                text,
                stepsTextPaint,
                measuredWidth / stepsTitles.size,
                Layout.Alignment.ALIGN_NORMAL,
                1f,
                0f,
                true
            )
        }
    }

    init {

        context.withStyledAttributes(attrs, R.styleable.StepProgressBar, defStyleAttr) {
            count = getInt(R.styleable.StepProgressBar_count, DEFAULT_COUNT)
            stepsRadius = getDimension(R.styleable.StepProgressBar_stepsRadius, DEFAULT_STEPS_RADIUS)
            titleTextSize = getDimension(R.styleable.StepProgressBar_titlesTextSize, DEFAULT_TEXT_SIZE)
            selectedStep = getInt(R.styleable.StepProgressBar_selectedStep, 0)
            completedStepsColor = getColor(R.styleable.StepProgressBar_stepsColor, completedStepsColor)
            unfulfilledStepColor = getColor(R.styleable.StepProgressBar_stepsDividerColor, unfulfilledStepColor)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        titlesLayouts = getTitlesLayouts()
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getViewHeight().toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val lineWidth = lineWidth
        val isTitlesEnabled = titlesLayouts.isNotEmpty()

        val stepsCount = if (isTitlesEnabled) count else titlesLayouts.size
        setPaintsColor(completedStepsColor)

        for (i in 0 until stepsCount) {
            val xPosition = getStepXPosition(i)

            if (selectedStep > i)
                drawCompletedStep(canvas, xPosition, yPosition)
            else
                drawUnfulfilledStep(canvas, xPosition, yPosition)

            if (isTitlesEnabled) {
                canvas?.save()
                canvas?.translate(xPosition, 2 * stepsRadius + TEXT_PADDING)
                titlesLayouts[i].draw(canvas)
                canvas?.restore()
            }

            if (i == selectedStep)
                setPaintsColor(unfulfilledStepColor)

            if (i < count)
                drawLine(canvas, xPosition + stepsRadius, yPosition, lineWidth)
        }
    }
}
