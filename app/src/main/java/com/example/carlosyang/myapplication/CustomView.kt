package com.example.carlosyang.myapplication

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.WindowManager

/**
 * Created by CarlosYang on 2017/1/12.
 */

class CustomView(context: Context) : View(context) {
    val r = 300f//px
    val left_circle = FloatArray(2)
    val refresh_internal = 1000 / 60L//ms
    var rotate_time = 0f//ms
    val rotate_speed = 100f//度/秒
    var trans_time = 0f//ms
    val trans_speed = 200f//px/s
    var canRotate: Boolean? = true
    var canTrans: Boolean? = false
    var drawLast: Boolean? = false
    val text_paint: Paint
    val dashPaint: Paint
    val linePaint: Paint
    val lineDashPaint: Paint
    val left_circle_path: Path
    val line_path: Path

    init {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.let {
            left_circle[0] = (it.width - r) / 2f
            left_circle[1] = (it.height + 0.866f * r) / 2f
        }

        left_circle_path = Path().apply {
            addCircle(left_circle[0], left_circle[1], r, Path.Direction.CW)
        }



        line_path = Path().apply {
            moveTo(left_circle[0], left_circle[1])
            lineTo(left_circle[0] + r, left_circle[1])
        }


        text_paint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.FILL
            textSize = 40f
        }


        val dash = DashPathEffect(floatArrayOf(20f, 10f), 1f)

        dashPaint = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 5f
            isAntiAlias = true
            pathEffect = dash
        }



        linePaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 5f
            isAntiAlias = true
        }



        lineDashPaint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.RED
            strokeWidth = 5f
            isAntiAlias = true
            pathEffect = dash
        }

    }

    override fun onDraw(canvas: Canvas) {
        canvas.apply {
            drawPath(left_circle_path, dashPaint)
            save()
            translate(-30f, 30f)
            drawText("A", left_circle[0], left_circle[1], text_paint)
            restore()
            save()
            translate(r, 0f)
            drawPath(left_circle_path, dashPaint)
            translate(20f, 20f)
            drawText("B", left_circle[0], left_circle[1], text_paint)
            restore()
            save()
            translate(0.5f * r, -0.866f * r)
            drawPath(left_circle_path, dashPaint)
            translate(30f, -30f)
            drawText("C", left_circle[0], left_circle[1], text_paint)
            restore()

            if (canRotate!!) {
                linePaint.color = Color.BLACK
                if (rotate_speed / 1000f * rotate_time >= 60) {
                    linePaint.color = Color.BLUE
                }
                save()
                rotate(-rotate_speed / 1000f * rotate_time, left_circle[0], left_circle[1])
                drawLine(left_circle[0], left_circle[1], left_circle[0] + r, left_circle[1], linePaint)
                rotate_time += refresh_internal.toFloat()
                restore()
                if (rotate_speed / 1000f * rotate_time >= 120) {
                    canRotate = false
                    canTrans = true
                }
            }

            if (canTrans!!) {
                save()
                translate(trans_time * trans_speed / 1000f, 0f)
                rotate(-120f, left_circle[0], left_circle[1])
                drawPath(line_path, lineDashPaint)
                trans_time += refresh_internal.toFloat()
                restore()
                if (trans_time * trans_speed / 1000f >= r) {
                    canTrans = false
                    drawLast = true
                }
            }

            if (rotate_speed / 1000f * rotate_time >= 60) {
                linePaint.color = Color.BLACK
                drawLine(left_circle[0], left_circle[1], left_circle[0] + r, left_circle[1], linePaint)
            }
            if (rotate_speed / 1000f * rotate_time >= 120) {
                linePaint.color = Color.BLUE
                save()
                rotate(-60f, left_circle[0], left_circle[1])
                drawLine(left_circle[0], left_circle[1], left_circle[0] + r, left_circle[1], linePaint)
                restore()
            }

            if (drawLast!!) {
                linePaint.color = Color.RED
                save()
                rotate(60f, left_circle[0] + r, left_circle[1])
                drawLine(left_circle[0], left_circle[1], left_circle[0] + r, left_circle[1], linePaint)
                restore()
            }
        }


        postInvalidateDelayed(refresh_internal)

    }

    fun resetView() {
        canRotate = true
        canTrans = false
        drawLast = false
        trans_time = 0f//ms
        rotate_time = 0f//ms
    }
}
