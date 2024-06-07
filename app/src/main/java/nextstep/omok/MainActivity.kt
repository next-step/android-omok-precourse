package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
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

    fun placeStone(view: ImageView, row: Int, col: Int) {
        if (table[row][col] == 0) {
            val stoneResource = getStoneResource()
            view.setImageResource(stoneResource)
            updateBoardState(row, col)
            if (checkWin(row, col)) handleWin()
        }
    }

    private fun getStoneResource(): Int = if (turn % 2 == 0) R.drawable.black_stone else R.drawable.white_stone

    private fun updateBoardState(row: Int, col: Int) {
        table[row][col] = if (turn % 2 == 0) 1 else 2
        turn++
    }

    private fun countStones(row: Int, col: Int, dx: Int, dy: Int): Int {
        return 1 + countDirection(row, col, dx, dy) + countDirection(row, col, -dx, -dy)
    }

    private fun countDirection(row: Int, col: Int, dx: Int, dy: Int): Int {
        var r = row + dx
        var c = col + dy
        var count = 0
        while (r in 0 until boardSize && c in 0 until boardSize && table[r][c] == table[row][col]) {
            count++
            r += dx
            c += dy
        }
        return count
    }

    private fun checkWin(row: Int, col: Int): Boolean {
        val directions = listOf(listOf(0, 1), listOf(1, 0), listOf(1, 1), listOf(1, -1))
        return directions.any { countStones(row, col, it[0], it[1]) >= 5 }
    }

    private fun handleWin() {
        val winner = getWinner()
        showWinDialog(winner)
    }

    private fun getWinner() = if (turn % 2 == 1) "흑" else "백"

    private fun showWinDialog(winner: String) {
        runOnUiThread {
            AlertDialog.Builder(this@MainActivity).apply {
                setTitle("게임 종료")
                setMessage("${winner}이 승리했습니다. 새 게임을 진행하시겠습니까?")
                setupDialogButtons()
                setCancelable(false)
                show()
            }
        }
    }

    private fun AlertDialog.Builder.setupDialogButtons() {
        setNegativeButton("No") { _,_ -> finish() }
        setPositiveButton("Yes") { _,_ -> finish(); startActivity(intent) }
    }
}