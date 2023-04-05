package com.example.memorygame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class MemoryGameView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val clipRectRight = resources.getDimension(R.dimen.clipRectRight)
    private val clipRectBottom = resources.getDimension(R.dimen.clipRectBottom)
    private val clipRectTop = resources.getDimension(R.dimen.clipRectTop)
    private val clipRectLeft = resources.getDimension(R.dimen.clipRectLeft)
    private val rectInset = resources.getDimension(R.dimen.rectInset)
    private val rectInsetTop = resources.getDimension(R.dimen.rectInsetTop)

    private val columnOne = rectInset
    private val columnTwo = columnOne + rectInset + clipRectRight
    private val rowOne = rectInsetTop

    private var click: Int = 0

    private val colorList = listOf(Color.RED, Color.BLUE, Color.GREEN)
    private var firstCardColor = Color.GRAY

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when (click) {
            0 -> {
                drawBackground(canvas)
            }
            1 -> {
                drawBackground(canvas)
                firstCardColor = colorList.random()
                drawClippedCard(canvas, firstCardColor)
            }
            2 -> {
                drawBackground(canvas)
                canvas.save()
                drawClippedCard(canvas, firstCardColor)
                canvas.restore()
                canvas.translate(columnTwo, rowOne)
                val secondCardColor = colorList.random()
                drawClippedCard(canvas, secondCardColor)
                Toast.makeText(
                    context,
                    if (firstCardColor == secondCardColor)
                        R.string.toast_congratulations
                    else
                        R.string.toast_try_again,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        canvas.save()
    }

    private fun drawClippedCard(canvas: Canvas, color: Int) {
        canvas.clipRect(
            clipRectLeft, clipRectTop, clipRectRight, clipRectBottom
        )
        canvas.drawColor(color)
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawColor(Color.GRAY)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        performClick()
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        click = if (click == 2) 0 else ++click
        this.invalidate()
        return super.performClick()
    }
}