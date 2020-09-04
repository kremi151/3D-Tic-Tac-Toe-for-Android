/**
 * 3D Tic Tac Toe for Android
 * Copyright (C) 2018  Michel Kremer (kremi151)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package lu.kremi151.a3dtictactoe.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import lu.kremi151.a3dtictactoe.R
import lu.kremi151.a3dtictactoe.enums.FieldValue
import lu.kremi151.a3dtictactoe.interfaces.FieldColorInterceptor
import lu.kremi151.a3dtictactoe.interfaces.OnBoardTapListener
import lu.kremi151.a3dtictactoe.util.GameCube
import kotlin.math.floor

class GameBoard : View {
    private var mContext: Context? = null
    private var fieldPaint: Paint? = null
    private var fieldContentPaint: Paint? = null
    private var fieldValuePaint: Paint? = null
    private var portrait = false
    private var mViewWidth = 0
    private var mViewHeight = 0
    private var layerSize = 0f
    private var layerSpacing = 0f
    private var fieldSize = 0f
    private var listener: OnBoardTapListener? = null
    private var fieldColorInterceptor: FieldColorInterceptor? = null
    private var valueColorInterceptor: FieldColorInterceptor? = null
    private var fieldColorDefault = 0
    private var cube = GameCube()

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    fun setCube(cube: GameCube): GameBoard {
        this.cube = cube
        return this
    }

    private fun init(context: Context) {
        mContext = context
        fieldColorDefault = context.resources.getColor(R.color.fieldColor)
        fieldPaint = Paint()
        fieldPaint!!.color = Color.DKGRAY
        fieldPaint!!.strokeWidth = 1f
        fieldPaint!!.style = Paint.Style.STROKE
        fieldValuePaint = Paint(fieldPaint)
        fieldValuePaint!!.strokeWidth = 4f
        fieldValuePaint!!.isAntiAlias = true
        fieldContentPaint = Paint()
        fieldContentPaint!!.color = Color.WHITE
        fieldContentPaint!!.style = Paint.Style.FILL
    }

    fun pause() {}
    fun resume() {}
    fun setListener(listener: OnBoardTapListener?) {
        this.listener = listener
    }

    fun setFieldColorInterceptor(interceptor: FieldColorInterceptor?) {
        fieldColorInterceptor = interceptor
    }

    fun setValueColorInterceptor(interceptor: FieldColorInterceptor?) {
        valueColorInterceptor = interceptor
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.drawColor(Color.WHITE)
        canvas.translate(1f, 1f)
        for (z in 0 until cube.depth()) {
            for (x in 0 until cube.width()) {
                for (y in 0 until cube.height()) {
                    var left: Float
                    var top: Float
                    if (portrait) {
                        left = z * layerSpacing + x * fieldSize
                        top = z * layerSize + y * fieldSize
                    } else {
                        left = z * layerSize + x * fieldSize
                        top = z * layerSpacing + y * fieldSize
                    }
                    if (fieldColorInterceptor != null) {
                        fieldContentPaint!!.color = fieldColorInterceptor!!.getFieldColor(x, y, z, Color.WHITE)
                        canvas.drawRect(
                                left,
                                top,
                                left + fieldSize,
                                top + fieldSize,
                                fieldContentPaint!!)
                    }
                    canvas.drawRect(
                            left,
                            top,
                            left + fieldSize,
                            top + fieldSize,
                            fieldPaint!!)
                    if (valueColorInterceptor != null) {
                        fieldValuePaint!!.color = valueColorInterceptor!!.getFieldColor(x, y, z, fieldColorDefault)
                    }
                    when (cube.valueAt(x, y, z)) {
                        FieldValue.CIRCLE -> canvas.drawCircle(left + fieldSize / 2, top + fieldSize / 2, fieldSize * 0.4f, fieldValuePaint!!)
                        FieldValue.CROSS -> drawCross(canvas, left + fieldSize * 0.1f, top + fieldSize * 0.1f, left + fieldSize * 0.9f, top + fieldSize * 0.9f, fieldValuePaint)
                    }
                }
            }
        }
        canvas.restore()
    }

    private fun drawCross(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float, paint: Paint?) {
        canvas.drawLine(left, top, right, bottom, paint!!)
        canvas.drawLine(left, bottom, right, top, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        var w = w
        var h = h
        super.onSizeChanged(w, h, oldw, oldh)
        w -= 2
        h -= 2
        portrait = h >= w
        mViewWidth = w
        mViewHeight = h
        if (portrait) {
            layerSize = h / 4.toFloat()
            layerSpacing = (w - layerSize) / 3
        } else {
            layerSize = w / 4.toFloat()
            layerSpacing = (h - layerSize) / 3
        }
        fieldSize = layerSize / 4
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val bX: Int
            val bY: Int
            val bZ: Int
            bZ = if (portrait) {
                floor(event.y / layerSize.toDouble()).toInt()
            } else {
                floor(event.x / layerSize.toDouble()).toInt()
            }
            if (portrait && event.x >= bZ * layerSpacing && event.x < bZ * layerSpacing + layerSize
                    || !portrait && event.y >= bZ * layerSpacing && event.y < bZ * layerSpacing + layerSize) {
                if (portrait) {
                    bY = floor(event.y % layerSize / fieldSize.toDouble()).toInt()
                    bX = floor((event.x - bZ * layerSpacing) % layerSize / fieldSize.toDouble()).toInt()
                } else {
                    bY = floor((event.y - bZ * layerSpacing) % layerSize / fieldSize.toDouble()).toInt()
                    bX = floor(event.x % layerSize / fieldSize.toDouble()).toInt()
                }
                if (listener != null && listener!!.onTap(bX, bY, bZ)) {
                    invalidate()
                }
            }
        }
        return true
    }
}