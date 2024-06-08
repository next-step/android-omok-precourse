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
    }}
