package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var putStoneLayout: GridLayout
    private val numRows = 15
    private val numCols = 15
    private var blackTurn = true
    val board = Array(numRows) { arrayOfNulls<String>(numCols) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        putStoneLayout = findViewById(R.id.putstonelayout)
        setImgviewListener(putStoneLayout)
    }

    private fun setImgviewListener(gridLayout: GridLayout) {
        for (i in 0 until gridLayout.childCount) {
            val imgView = gridLayout.getChildAt(i) as ImageView
            imgView.setOnClickListener {
                handleStonePlacement(imgView, i)
            }
        }
    }

    private fun handleStonePlacement(imgView: ImageView, id: Int) {
        val row = id / numRows
        val col = id % numCols
        if (isValidPosition(row, col) == true) {
            placeStone(row, col, imgView)
            if (checkWin(row, col)) {
                showWinMsg(blackTurn)
            }
            switchTurn()
        }
    }

    fun isValidPosition(row: Int, col: Int): Boolean? {
        return if (row in 0 until numRows && col in 0 until numCols) {
            board[row][col] == null
        } else {
            Log.e("Exception", "out of range : row $row, col $col")
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
            listOf(0 to 1, 0 to -1),
            listOf(1 to 0, -1 to 0),
            listOf(1 to 1, -1 to -1),
            listOf(1 to -1, -1 to 1),
        )
        return directions.any { direction ->
            val count = 1 + countStonesInDirection(row, col, direction[0], color) + countStonesInDirection(row, col, direction[1], color)
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

    private fun showWinMsg(blackTurn: Boolean) {
        val winner = whoIsWinner(blackTurn)
        AlertDialog.Builder(this)
            .setTitle("Game Over")
            .setMessage("$winner wins!")
            .setPositiveButton("EXIT") { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }

    private fun whoIsWinner(isBlackTurn: Boolean): String {
        return if (isBlackTurn) "Black" else "White"
    }
}
