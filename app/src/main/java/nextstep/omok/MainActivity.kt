package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private val BLACK_STONE: Int = 0
    private val WHITE_STONE: Int = 1
    private val BLANK: Int = -1
    private val INITIAL_STONE_COUNT: Int = 1

    private var turn: Int = 0
    private val boardSize: Int = 15
    private var boardState = Array(boardSize){ IntArray(boardSize) {BLANK} }
    private val directions: Array<Array<Int>> = arrayOf(
        arrayOf(0, 1),
        arrayOf(1, 0),
        arrayOf(1, 1),
        arrayOf(1, -1)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBoard()

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed {index, view -> view.setOnClickListener {
                placeStone(index, view)
            } }

    }

    private fun placeStone(index: Int, view: ImageView) {
        val (row, column) = calculatePosition(index)

        if (!hasStone(view)) {
            val stoneColor = setStoneColor()

            setStoneImageView(stoneColor, view)
            updateBoardState(row, column, stoneColor)

            if (checkWin(row, column, stoneColor)) {
                Log.d("testt", "" + stoneColor + " 승리")
            }
            checkGameOver()
            addTurn()
        }
    }

    private fun updateBoardState(row: Int, column: Int, stoneColor: Int) {
        boardState[row][column] = stoneColor
    }

    private fun calculatePosition(index: Int): Pair<Int, Int> {
        val row = index / boardSize
        val column = index % boardSize
        Log.d("testt", "(" + row + ", " + column + ")")
        return Pair(row, column)
    }

    private fun initBoard() {
        boardState = Array(boardSize) { IntArray(boardSize) { BLANK } }
    }

    private fun setStoneImageView(stoneColor: Int, view: ImageView) {
        val stoneImage = if (stoneColor == BLACK_STONE) R.drawable.black_stone else R.drawable.white_stone
        view.setImageResource(stoneImage)
    }

    private fun hasStone(view: ImageView): Boolean {
        if (view.drawable != null) return true
        else return false
    }

    private fun setStoneColor():Int {
        if(turn % 2 == BLACK_STONE) return BLACK_STONE
        else return WHITE_STONE
    }

    private fun addTurn() {
        turn++
    }

    fun checkWin(row: Int, column: Int, stoneColor: Int): Boolean {
        for ((dx, dy) in directions) {
            var count = INITIAL_STONE_COUNT

            count += countStonesInDirection(row, column, dx, dy, stoneColor)
            count += countStonesInDirection(row, column, -dx, -dy, stoneColor)

            Log.d("testt","방향 ("+dx+","+dy+")"+" 연속되는 돌 개수:"+count)

            if (count == 5) return true
        }
        return false
    }
    fun countStonesInDirection(row: Int, col: Int, dx: Int, dy: Int, stoneColor: Int): Int {
        var count = 0
        var x = row + dx
        var y = col + dy
        while (x in 0..14 && y in 0..14 
            && boardState[x][y] == stoneColor) {
            count++
            
            x += dx
            y += dy
        }
        return count
    }

    private fun checkGameOver() {
    }

}
