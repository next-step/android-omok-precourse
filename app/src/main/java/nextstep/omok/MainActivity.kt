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

    fun checkGameSet(row: Int, col: Int): Boolean {
        for (direction in derivative) {
            var count = 1

            count += countStones(row, col, direction[0], direction[1])
            count += countStones(row, col, -direction[0], -direction[1])

            if (count >= 5) {
                return true
            }
        }
        return false
    }

    fun countStones(row: Int, col: Int, rowDir: Int, colDir: Int): Int {
        var currentRow = row + rowDir
        var currentCol = col + colDir
        var count = 0

        while (currentRow in 0 until boardSize && currentCol in 0 until boardSize && boardState[currentRow][currentCol] == player) {
            count++
            currentRow += rowDir
            currentCol += colDir
        }
        return count
    }

    private fun changePlayer() {
        player = if (player == "black") "white" else "black"
    }

    private fun showWinner() {
        val showWinner = findViewById<TextView>(R.id.showWinner)
        showWinner.text = if (winnerPlayer == "black") "흑돌 승리!" else "백돌 승리!"
        gameOver = true
    }
    private fun showDraw() {
        val showWinner = findViewById<TextView>(R.id.showWinner)
        showWinner.text = "무승부!"
        gameOver = true
    }

    private fun isBoardFull(): Boolean {
        for (row in boardState) {
            for (cell in row) {
                if (cell == "") {
                    return false
                }
            }
        }
        return true
    }
}
