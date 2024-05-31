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

}
