package nextstep.omok

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(context: Context) : View(context) {
    private val boardSize = 15
    private val cellSize = 70f
    private val boardPaint = Paint().apply { color = Color.BLACK }
    private val blackPaint = Paint().apply { color = Color.BLACK }
    private val whitePaint = Paint().apply { color = Color.WHITE }
    private val game = OmokGame(boardSize)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBoard(canvas)
        drawStones(canvas)
    }

    private fun drawBoard(canvas: Canvas) {
        for (i in 0 until boardSize) {
            canvas.drawLine(i * cellSize, 0f, i * cellSize, boardSize * cellSize, boardPaint)
            canvas.drawLine(0f, i * cellSize, boardSize * cellSize, i * cellSize, boardPaint)
        }
    }

    private fun drawStones(canvas: Canvas) {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                when (game.getStone(i, j)) {
                    Stone.BLACK -> canvas.drawCircle(
                        i * cellSize + cellSize / 2, j * cellSize + cellSize / 2, cellSize / 2, blackPaint
                    )
                    Stone.WHITE -> canvas.drawCircle(
                        i * cellSize + cellSize / 2, j * cellSize + cellSize / 2, cellSize / 2, whitePaint
                    )
                    else -> {}
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = (event.x / cellSize).toInt()
            val y = (event.y / cellSize).toInt()
            if (game.placeStone(x, y)) {
                invalidate()
            }
            return true
        }
        return super.onTouchEvent(event)
    }
}