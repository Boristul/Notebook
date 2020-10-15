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

@Suppress("TooManyFunctions")
class StepProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_COUNT = 4
        private const val DEFAULT_STEPS_RADIUS = 40f
        private const val DEFAULT_TEXT_SIZE = 40f

        // TODO: create param in xml and code
        private const val TEXT_PADDING = 20f
    }

    var onStepChangedListener: ((item: Int) -> Unit)? = null

    var count: Int = DEFAULT_COUNT
        set(value) {
            field = value
            invalidate()
        }

    @Dimension(unit = Dimension.DP)
    var stepRadius: Float = DEFAULT_STEPS_RADIUS
        set(value) {
            field = value
            requestLayout()
        }

    private val stepDiameter
        get() = stepRadius * 2

    @Dimension(unit = Dimension.SP)
    var titleTextSize: Float = DEFAULT_TEXT_SIZE
        set(value) {
            field = value
            stepsTextPaint.textSize = value
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
        get() = stepRadius + paddingTop

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
        canvas?.drawCircle(xPosition, yPosition, stepRadius, stepsPaint)
    }

    @Suppress("MagicNumber")
    private fun drawUnfulfilledStep(canvas: Canvas?, xPosition: Float, yPosition: Float) {
        canvas?.drawCircle(xPosition, yPosition, stepRadius / 4, stepsPaint)
        canvas?.drawCircle(xPosition, yPosition, stepRadius, stepsStrokePaint)
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

    private fun getLineWidth(isTitlesEnabled: Boolean) = if (isTitlesEnabled) {
        titlesLayouts[0].width - stepDiameter
    } else {
        (width - (stepDiameter * count)) / (count - 1)
    }

    private fun getStepXPosition(stepIndex: Int, isTitlesEnabled: Boolean): Float = if (isTitlesEnabled) {
        (titlesLayouts[stepIndex].width * (0.5 + stepIndex)).toFloat()
    } else {
        (stepRadius * 2 + getLineWidth(isTitlesEnabled)) * stepIndex + stepRadius
    }

    private fun getViewHeight(): Float = stepDiameter + getStepsTitlesHeight() + paddingTop + paddingBottom + TEXT_PADDING

    private fun getStepsTitlesHeight(): Int = titlesLayouts.map { it.height }.maxOrNull() ?: 0

    private fun initTitlesLayouts() {
        titlesLayouts = stepsTitles.map { text ->
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
    }

    init {

        context.withStyledAttributes(attrs, R.styleable.StepProgressBar, defStyleAttr) {
            count = getInt(R.styleable.StepProgressBar_count, DEFAULT_COUNT)
            stepRadius = getDimension(R.styleable.StepProgressBar_stepRadius, DEFAULT_STEPS_RADIUS)
            titleTextSize = getDimension(R.styleable.StepProgressBar_titlesTextSize, DEFAULT_TEXT_SIZE)
            selectedStep = getInt(R.styleable.StepProgressBar_selectedStep, 0)
            completedStepsColor = getColor(R.styleable.StepProgressBar_stepsColor, completedStepsColor)
            unfulfilledStepColor = getColor(R.styleable.StepProgressBar_stepsDividerColor, unfulfilledStepColor)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        initTitlesLayouts()
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getViewHeight().toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val isTitlesEnabled = titlesLayouts.isNotEmpty()
        val lineWidth = getLineWidth(isTitlesEnabled)

        val stepsCount = if (isTitlesEnabled) titlesLayouts.size else count
        setPaintsColor(completedStepsColor)

        for (i in 0 until stepsCount) {
            val xPosition = getStepXPosition(i, isTitlesEnabled)

            if (selectedStep > i) {
                drawCompletedStep(canvas, xPosition, yPosition)
            } else {
                drawUnfulfilledStep(canvas, xPosition, yPosition)
            }

            if (isTitlesEnabled) {
                canvas?.save()
                canvas?.translate(xPosition, stepDiameter + TEXT_PADDING)
                titlesLayouts[i].draw(canvas)
                canvas?.restore()
            }

            if (i == selectedStep) setPaintsColor(unfulfilledStepColor)

            if (i < stepsCount - 1) drawLine(canvas, xPosition + stepRadius, yPosition, lineWidth)
        }
    }
}
