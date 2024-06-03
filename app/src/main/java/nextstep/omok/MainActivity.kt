package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    companion object {
        const val BOARD_SIZE = 15
    }

    val blackPlayer = Player("Black", R.drawable.black_stone, R.drawable.black_stone_highlight)
    val whitePlayer = Player("White", R.drawable.white_stone, R.drawable.white_stone_highlight)

    var currentPlayer: Player = blackPlayer
    lateinit var board: TableLayout
    lateinit var blackPlayerFlag: ImageView
    lateinit var whitePlayerFlag: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = findViewById(R.id.board)
        blackPlayerFlag = findViewById(R.id.black_player_flag)
        whitePlayerFlag = findViewById(R.id.white_player_flag)

        initializeBoard(board)
        updatePlayerFlag()
    }

    private fun initializeBoard(board: TableLayout) {
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, tableRow ->
            tableRow.children.filterIsInstance<Cell>().forEachIndexed { colIndex, cell ->
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
                updatePlayerFlag()
            }
        }
    }

    private fun logCellPosition(position: Pair<Int, Int>) {
        Log.d("testt", "Row: ${position.first}, Column: ${position.second}")
    }

    private fun updatePlayerFlag() {
        when (currentPlayer) {
            blackPlayer -> {
                blackPlayerFlag.setImageResource(blackPlayer.highlightResId)
                whitePlayerFlag.setImageResource(whitePlayer.stoneResId)
            }
            whitePlayer -> {
                blackPlayerFlag.setImageResource(blackPlayer.stoneResId)
                whitePlayerFlag.setImageResource(whitePlayer.highlightResId)
            }
        }
    }

    private fun switchPlayer() {
        currentPlayer = when (currentPlayer) {
            blackPlayer -> whitePlayer
            whitePlayer -> blackPlayer
            else -> currentPlayer
        }
    }
}
