package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

val derivative = arrayOf(
    arrayOf(1, 0), arrayOf(0, 1), arrayOf(1, 1), arrayOf(1, -1)
)

class MainActivity : AppCompatActivity() {
    private val boardSize: Int = 15;
    private var player: String = "black"
    private var winnerPlayer: String = ""
    private var gameOver: Boolean = false
    private val boardState = Array(boardSize) { Array(boardSize) { "" } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener { onCellClicked(index, view) }
            }

    }

    fun onCellClicked(index: Int, view: ImageView) {
        if (gameOver) {
            return
        }

        val row: Int = index / boardSize
        val col: Int = index % boardSize
        if (boardState[row][col] == "") {
            placeStone(view)
            boardState[row][col] = player
            if (checkGameSet(row, col)) {
                winnerPlayer = player
                showWinner()
            } else if (isBoardFull()) {
                showDraw()
            } else {
                changePlayer()
            }
        }
    }
    private fun placeStone(view: ImageView) {
        if (player == "black") {
            view.setImageResource(R.drawable.black_stone)
        } else {
            view.setImageResource(R.drawable.white_stone)
        }
    }

}
