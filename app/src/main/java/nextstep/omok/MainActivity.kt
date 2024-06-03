package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    companion object {
        const val BOARD_SIZE = 15
    }

    val blackPlayer = Player("Black", R.drawable.black_stone)
    val whitePlayer = Player("White", R.drawable.white_stone)

    var currentPlayer: Player = blackPlayer
    lateinit var board: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = findViewById(R.id.board)
        initializeBoard(board)
    }

    private fun initializeBoard(board: TableLayout) {
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                tableRow.children
                    .filterIsInstance<Cell>()
                    .forEachIndexed { colIndex, cell ->
                        cell.position = Pair(rowIndex, colIndex)
                        cell.setOnClickListener { onCellClick(cell) }
                    }
            }
    }

    private fun onCellClick(cell: Cell) {
        val position = cell.position
        if (position != null) {
            logCellPosition(position)

            if (cell.isEmpty()) {
                cell.placeStone(currentPlayer.stoneResId)
                switchPlayer()
            }
        }
    }

    private fun logCellPosition(position: Pair<Int, Int>) {
        Log.d("testt", "Row: ${position.first}, Column: ${position.second}")
    }

    private fun switchPlayer() {
        currentPlayer = when (currentPlayer) {
            blackPlayer -> whitePlayer
            else -> blackPlayer
        }
    }
}
