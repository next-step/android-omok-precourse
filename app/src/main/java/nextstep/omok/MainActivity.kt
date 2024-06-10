package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private val boardSize = 15
    private var currentPlayer = "black"
    private val boardState = Array(boardSize) { Array(boardSize) { "" } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                row.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, imageView ->
                    imageView.setOnClickListener {
                        if (boardState[rowIndex][colIndex].isEmpty()) {
                            boardState[rowIndex][colIndex] = currentPlayer
                            imageView.setImageResource(
                                if (currentPlayer == "black") R.drawable.black_stone else R.drawable.white_stone
                            )
                            if (checkWin(rowIndex, colIndex)) {
                                showGameOverDialog("$currentPlayer wins!")
                            } else {
                                currentPlayer = if (currentPlayer == "black") "white" else "black"
                            }
                        }
                    }
                }
            }
    }

    private fun checkWin(row: Int, col: Int): Boolean {
        val directions = listOf(
            listOf(Pair(0, 1), Pair(0, -1)), // horizontal
            listOf(Pair(1, 0), Pair(-1, 0)), // vertical
            listOf(Pair(1, 1), Pair(-1, -1)), // diagonal down-right and up-left
            listOf(Pair(1, -1), Pair(-1, 1)) // diagonal down-left and up-right
        )

        for (direction in directions) {
            var count = 1
            for (dir in direction) {
                var r = row
                var c = col
                while (true) {
                    r += dir.first
                    c += dir.second
                    if (r in 0 until boardSize && c in 0 until boardSize && boardState[r][c] == currentPlayer) {
                        count++
                        if (count >= 5) return true
                    } else {
                        break
                    }
                }
            }
        }
        return false
    }

    private fun showGameOverDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Game Over")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                resetBoard()
            }
            .show()
    }

    private fun resetBoard() {
        currentPlayer = "black"
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                boardState[i][j] = ""
            }
        }
        val board = findViewById<TableLayout>(R.id.board)
        board.children.filterIsInstance<TableRow>().forEach { row ->
            row.children.filterIsInstance<ImageView>().forEach { imageView ->
                imageView.setImageResource(0)
            }
        }
    }
}
