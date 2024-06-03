package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

sealed class Result {
    object Success : Result()
    data class Failure(val reason: String) : Result()
}

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "흑돌"
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeGame()
    }

    private fun initializeGame() {
        showMessage("흑돌 차례입니다.")
        setupBoard()
    }

    private fun setupBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { imageView ->
                imageView.setOnClickListener { view ->
                    if (gameActive) placeStone(view as ImageView)
                }
            }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun placeStone(view: ImageView) {
        if (view.drawable != null) return
        setImageForCurrentPlayer(view)
        if (checkWin(view)) handleWin()
        else switchPlayer()
    }

    private fun setImageForCurrentPlayer(view: ImageView) {
        val resource = if (currentPlayer == "흑돌") R.drawable.black_stone else R.drawable.white_stone
        view.setImageResource(resource)
        view.tag = currentPlayer
    }

    private fun checkWin(view: ImageView): Boolean {
        val directions = listOf(Pair(1, 0), Pair(0, 1), Pair(1, 1), Pair(1, -1))
        return directions.any { checkDirection(view, it.first, it.second) }
    }

    private fun handleWin() {
        gameActive = false
        showDialog("$currentPlayer 승리")
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == "흑돌") "백돌" else "흑돌"
        showMessage("$currentPlayer 차례입니다.")
    }

    private fun checkDirection(view: ImageView, rowDelta: Int, colDelta: Int): Boolean {
        val board = findViewById<TableLayout>(R.id.board)
        val row = (view.parent as? TableRow)?.let { board.indexOfChild(it) } ?: return false
        val col = (view.parent as? TableRow)?.indexOfChild(view) ?: return false

        return countStones(board, row, col, rowDelta, colDelta) >= 5
    }

    private fun countStones(board: TableLayout, row: Int, col: Int, rowDelta: Int, colDelta: Int): Int {
        var count = 1
        count += countDirection(board, row, col, rowDelta, colDelta, currentPlayer)
        count += countDirection(board, row, col, -rowDelta, -colDelta, currentPlayer)
        return count
    }

    private fun countDirection(board: TableLayout, row: Int, col: Int, rowDelta: Int, colDelta: Int, player: String): Int {
        var count = 0
        var newRow = row + rowDelta
        var newCol = col + colDelta
        while (newRow in 0 until board.childCount && newCol in 0 until (board.getChildAt(newRow) as TableRow).childCount) {
            val cell = (board.getChildAt(newRow) as TableRow).getChildAt(newCol) as? ImageView ?: return count
            if (cell.tag == player) count++ else break
            newRow += rowDelta
            newCol += colDelta
        }
        return count
    }

}
