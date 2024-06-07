package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private var turn = 0
    private val boardSize = 15
    private var table = Array(boardSize) { IntArray(boardSize) { 0 } }
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
            imageView.setOnClickListener {
                placeStone(imageView, rowIndex, columnIndex)
            }
        }
    }

    private fun getStoneResource(): Int = if (turn % 2 == 0) R.drawable.black_stone else R.drawable.white_stone

    fun placeStone(view: ImageView, row: Int, col: Int) {
        if (table[row][col] == 0) {
            val stoneResource = getStoneResource()
            view.setImageResource(stoneResource)
            updateBoardState(row, col)
        }
    }

    private fun updateBoardState(row: Int, col: Int) {
        table[row][col] = if (turn % 2 == 0) 1 else 2
        turn++
    }
    private fun countStones() {}

    private fun countDirection() {}

    private fun checkWin() {}

    private fun handleWin() {}

    private fun showWinDialog() {}

}



