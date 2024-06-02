package nextstep.omok

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
class Game(
    private val numRows: Int = 15,
    private val numCols: Int = 15,
    private var blackTurn: Boolean = true
){

    val board = Array(numRows) { arrayOfNulls<String>(numCols) }


    private fun handleStonePlacement(imgView: ImageView, id: Int) {
        val row = id/numRows
        val col = id%numCols
        if (isValidPosition(row, col) == true) {
            placeStone(row, col, imgView)
            if (checkWin(row, col)) {
                Log.d("mytag", "Game Over")
            }
            switchTurn()
        }
    }

    fun isValidPosition(row: Int, col: Int): Boolean? {
        return if (row in 0 until numRows && col in 0 until numCols) {
            board[row][col] == null
        } else {
            Log.d("Exception", "out of range : row $row, col $col")
            null
        }
    }

    fun placeStone(row: Int, col: Int, imgView: ImageView) {
        board[row][col] = if (blackTurn) "black" else "white"
        imgView.setImageResource(if (blackTurn) R.drawable.black_stone else R.drawable.white_stone)
    }

    fun switchTurn() {
        blackTurn = !blackTurn
    }

    fun checkWin(row: Int, col: Int): Boolean {
        val color = board[row][col] ?: return false
        val directions = listOf(
            listOf(0 to 1, 0 to -1), // horizontal
            listOf(1 to 0, -1 to 0), // vertical
            listOf(1 to 1, -1 to -1), // diagonal \
            listOf(1 to -1, -1 to 1) // diagonal /
        )
        return directions.any { direction ->
            val count = 1 + countStonesInDirection(row, col, direction[0], color) +
                    countStonesInDirection(row, col, direction[1], color)
            count >= 5
        }
    }

    fun countStonesInDirection(row: Int, col: Int, direction: Pair<Int, Int>, color: String): Int {
        var count = 0
        var newRow = row + direction.first
        var newCol = col + direction.second
        while (newRow in 0 until numRows && newCol in 0 until numCols && board[newRow][newCol] == color) {
            count++
            newRow += direction.first
            newCol += direction.second
        }
        return count
    }

}