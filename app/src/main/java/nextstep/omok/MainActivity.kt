package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private val BOARD_SIZE = 15
    private val BOARD_ARRAY = Array(BOARD_SIZE) { IntArray(BOARD_SIZE) }
    private var isBlackTurn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeBoard()
    }

    private fun initializeBoard() {
        findViewById<TableLayout>(R.id.board).apply {
            children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
                row.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, view ->
                    view.setOnClickListener { onCellClicked(view, rowIndex, colIndex) }
                    view.setImageResource(0)
                }
            }
        }
    }

    private fun onCellClicked(view: ImageView, rowIndex: Int, colIndex: Int) {
        if (isCellOccupied(rowIndex, colIndex)) {
            return
        }
        placeStone(view, rowIndex, colIndex)
        if (checkWin(rowIndex, colIndex)) {
            return
        } else {
            isBlackTurn = !isBlackTurn
        }
    }

    private fun isCellOccupied(rowIndex: Int, colIndex: Int): Boolean {
        return BOARD_ARRAY[rowIndex][colIndex] != 0
    }

    private fun placeStone(view: ImageView, rowIndex: Int, colIndex: Int) {
        BOARD_ARRAY[rowIndex][colIndex] = if (isBlackTurn) 1 else 2
        view.setImageResource(if (isBlackTurn) R.drawable.black_stone else R.drawable.white_stone)
    }

    private fun checkWin(row: Int, col: Int): Boolean {
        val player = BOARD_ARRAY[row][col]
        val directions = listOf(Pair(1, 0), Pair(0, 1), Pair(1, 1), Pair(1, -1))
        return directions.any { direction ->
            countStones(row, col, direction.first, direction.second, player)
            + countStones(row, col, -direction.first, -direction.second, player) > 3
        }
    }

    private fun countStones(row: Int, col: Int, dRow: Int, dCol: Int, player: Int): Int {
        var count = 0
        var r = row + dRow
        var c = col + dCol

        while (r in 0 until BOARD_SIZE && c in 0 until BOARD_SIZE && BOARD_ARRAY[r][c] == player) {
            count++
            r += dRow
            c += dCol
        }
        return count
    }

}
