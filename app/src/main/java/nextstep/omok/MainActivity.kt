package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

const val BOARDSIZE = 15

const val EMPTY = 0
const val BLACK = 1
const val WHITE = 2
const val TIE = 3

const val WINCOUNT = 5

val derivative = arrayOf(
    arrayOf(1, 0), arrayOf(0, 1), arrayOf(1, 1), arrayOf(1, -1)
)

class MainActivity : AppCompatActivity() {

    var turn = 1
    var player = BLACK
    var winner = EMPTY
    val boardState = Array(BOARDSIZE) { Array(BOARDSIZE) { EMPTY } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { idx, view ->
                view.setOnClickListener { game(idx, view) }
            }

        showGameState()
    }

    fun game(idx: Int, view: ImageView) {
        val (row, column) = getPosition(idx)
        if (winner != EMPTY || boardState[row][column] != EMPTY) return

        placeStone(row, column, view)
        checkWinner(row, column)
        updateGameState()
        showGameState()
    }

    fun placeStone(row: Int, column: Int, view: ImageView) {
        if (player == BLACK) {
            view.setImageResource(R.drawable.black_stone)
            boardState[row][column] = BLACK
        } else {
            view.setImageResource(R.drawable.white_stone)
            boardState[row][column] = WHITE
        }
    }

    fun getPosition(idx: Int): Pair<Int, Int> {
        val row: Int = idx / BOARDSIZE
        val column: Int = idx % BOARDSIZE
        return Pair(row, column)
    }

    fun checkWinner(row: Int, column: Int) {
        if (turn == BOARDSIZE * BOARDSIZE) winner = TIE

        for ((dx, dy) in derivative) {
            var count = 1

            count += countStone(row, column, dx, dy)
            count += countStone(row, column, -dx, -dy)

            if (count >= WINCOUNT) {
                winner = player
            }
        }
    }

    fun countStone(row: Int, column: Int, dx: Int, dy: Int): Int {
        var count = 0
        var nx = row
        var ny = column
        while (true) {
            nx += dx
            ny += dy
            if (nx < 0 || nx >= BOARDSIZE || ny < 0 || ny >= BOARDSIZE) break
            if (boardState[nx][ny] != player) break
            count += 1
        }
        return count
    }