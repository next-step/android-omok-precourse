package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private var currentPlayer = Player.BLACK
    private val boardSize = 15
    private var boardState = Array(boardSize) { Array(boardSize) { Player.NONE } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBoard()
    }

    private fun setupBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, tableRow ->
            setupRow(tableRow, rowIndex)
        }
    }


    private fun setupRow(tableRow: TableRow, rowIndex: Int) {
        tableRow.children.filterIsInstance<ImageView>().forEachIndexed { columnIndex, imageView ->
            imageView.setOnClickListener { handleCellClick(rowIndex, columnIndex) }
        }
    }

    private fun handleCellClick(rowIndex: Int, columnIndex: Int) {
        placeStone(rowIndex, columnIndex)?.let {
            if (it) {
                if (checkWin(rowIndex, columnIndex)) {
                    endGame()
                } else {
                    togglePlayer()
                }
            }
        }
    }


    private fun placeStone(rowIndex: Int, columnIndex: Int): Boolean? {
        return if (boardState[rowIndex][columnIndex] == Player.NONE) {
            boardState[rowIndex][columnIndex] = currentPlayer
            updateBoardView(rowIndex, columnIndex)
            true
        } else {
            null
        }
    }

    private fun updateBoardView(rowIndex: Int, columnIndex: Int) {
        getCellImageView(rowIndex, columnIndex)?.let { cell ->
            val resource = when (currentPlayer) {
                Player.BLACK -> R.drawable.black_stone
                Player.WHITE -> R.drawable.white_stone
                else -> android.R.color.transparent
            }
            cell.setImageResource(resource)
            cell.isEnabled = false
        }
    }

    private fun checkWin(rowIndex: Int, columnIndex: Int): Boolean {
        return listOf(
            checkDirection(rowIndex, columnIndex, 1, 0),
            checkDirection(rowIndex, columnIndex, 0, 1),
            checkDirection(rowIndex, columnIndex, 1, 1),
            checkDirection(rowIndex, columnIndex, 1, -1)
        ).any { it }
    }

    private fun checkDirection(row: Int, col: Int, dRow: Int, dCol: Int): Boolean {
        val count = 1 + countStones(row, col, dRow, dCol) + countStones(row, col, -dRow, -dCol)
        return count >= 5
    }

    private fun countStones(row: Int, col: Int, dRow: Int, dCol: Int): Int {
        var count = 0
        var r = row + dRow
        var c = col + dCol
        while (r in 0 until boardSize && c in 0 until boardSize && boardState[r][c] == currentPlayer) {
            count++
            r += dRow
            c += dCol
        }
        return count
    }


    private fun togglePlayer() {
        currentPlayer = if (currentPlayer == Player.BLACK) Player.WHITE else Player.BLACK
    }

    private fun resetBoard() {
        boardState = Array(boardSize) { Array(boardSize) { Player.NONE } }
        val board = findViewById<TableLayout>(R.id.board)
        board.children.filterIsInstance<TableRow>().forEach { tableRow ->
            tableRow.children.filterIsInstance<ImageView>().forEach { imageView ->
                imageView.setImageResource(android.R.color.transparent)
                imageView.isEnabled = true
            }
        }
        currentPlayer = Player.BLACK
    }


    private fun endGame() {
        Toast.makeText(this, "${currentPlayer.name} 승리 하셨습니다 축하축하!!!!!", Toast.LENGTH_LONG).show()
        resetBoard()
    }

       private fun getCellImageView(rowIndex: Int, columnIndex: Int): ImageView? {
        val board = findViewById<TableLayout>(R.id.board)
        return board.getChildAt(rowIndex)?.let { row ->
            (row as TableRow).getChildAt(columnIndex) as? ImageView
        }
    }

    enum class Player {
        BLACK, WHITE, NONE
    }


}
