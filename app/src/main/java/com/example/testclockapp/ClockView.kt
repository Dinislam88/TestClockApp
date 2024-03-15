package com.example.testclockapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import java.util.*

class ClockView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint()
    private var radius = 0f
    private var centerX = 0f
    private var centerY = 0f
    private val calendar = Calendar.getInstance()

    init {
        paint.color = Color.BLACK
        paint.strokeWidth = 8f
        paint.style = Paint.Style.STROKE

        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                invalidate()
                handler.postDelayed(this, 1000)
            }
        })
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (Math.min(w, h) / 2 * 0.8).toFloat()
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)

        drawClockFace(canvas)

        val hour = calendar.get(Calendar.HOUR_OF_DAY) % 12
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        drawHand(canvas, ((hour + minute / 60.0).toFloat() * 5f), true)
        drawHand(canvas, minute.toFloat(), false)
        drawHand(canvas, second.toFloat(), false)
    }

    private fun drawHand(canvas: Canvas, loc: Float, isHour: Boolean) {
        val angle = Math.PI * loc / 30 - Math.PI / 2
        val handRadius = if (isHour) radius * 0.5 else radius * 0.8
        canvas.drawLine(centerX, centerY,
            (centerX + handRadius * Math.cos(angle)).toFloat(),
            (centerY + handRadius * Math.sin(angle)).toFloat(), paint)
    }

    private fun drawClockFace(canvas: Canvas) {
        paint.textSize = 50f
        paint.textAlign = Paint.Align.CENTER

        for (i in 1..12) {
            val angle = Math.PI / 6 * (i - 3)
            val x = centerX + Math.cos(angle) * radius * 0.85
            val y = centerY + Math.sin(angle) * radius * 0.85
            canvas.drawText(i.toString(), x.toFloat(), y.toFloat(), paint)
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return superState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)
    }
}