package nextstep.omok

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
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
    private var isGameOver: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        initBoard(board)

        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed {index, view -> view.setOnClickListener {
                placeStoneOnView(index, view)
            } }
    }

    private fun placeStoneOnView(index: Int, view: ImageView) {
        val (row, column) = calculatePosition(index)

        if (!hasStone(view)) {
            val stoneColor = setStoneColor()

            setStoneImageView(stoneColor, view)
            updateBoardState(row, column, stoneColor)

            if (checkWin(row, column, stoneColor)) {
                var winner: String = ""
                if (stoneColor == BLACK_STONE) winner = "검정 돌"
                else winner = "흰 돌"

                val builder = AlertDialog.Builder(this)
                builder.setTitle("게임 종료")
                    .setMessage(winner + "이 이겼습니다.")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, id ->

                        })
                builder.show()
            }
            addTurn()
            checkGameOver()

            if(isGameOver) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("게임 종료")
                    .setMessage("무승부 입니다.")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, id ->

                        })
            }
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

    private fun initBoard(board: TableLayout) {
        boardState = Array(boardSize) { IntArray(boardSize) { BLANK } }

        isGameOver = false
        turn = 0

        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { it.setImageDrawable(null) }
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

            if (count >= 5) return true
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
        for (row in boardState) {
            for (cell in row) {
                if (cell == BLANK) {
                    return
                }
            }
        }
        isGameOver = true
        Log.d("testt", "Game Over")
    }

}
