package nextstep.omok

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow

class Board(private val tableLayout: TableLayout, private val cellClickListener: (Int, Int) -> Unit) {
    private val boardCells = Array(15) { arrayOfNulls<ImageView>(15) }

    fun initialize() {
        for (i in 0 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(i) as TableRow
            for (j in 0 until row.childCount) {
                val cell = row.getChildAt(j) as ImageView
                boardCells[i][j] = cell
                cell.setOnClickListener { cellClickListener(i, j) }
            }
        }
    }

    fun updateCell(row: Int, col: Int, player: Player) {
        val cell = boardCells[row][col]
        cell?.setImageResource(if (player == Player.BLACK) R.drawable.black_stone else R.drawable.white_stone)
    }
}
