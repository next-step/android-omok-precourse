package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

const val PLAYER_BLACK = 'B'
const val PLAYER_WHITE = 'W'
const val BOARD_SIZE = 15

class MainActivity : AppCompatActivity() {

    private var currentPlayer = PLAYER_BLACK
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeBoard()
        initializePlayer()
        setCellClickListener()
    }

    private fun initializeBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        board.children
            .filterIsInstance<TableRow>()
            .forEach { row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEach { view ->
                        view.setImageResource(0)
                        view.tag = null
                    }
            }
    }

    private fun initializePlayer() {
        currentPlayer = PLAYER_BLACK
    }

    private fun setCellClickListener() {
        val board = findViewById<TableLayout>(R.id.board)
        board.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, view ->
                        view.setOnClickListener { processClickedCell(rowIndex, colIndex) }
                    }
            }
    }

    private fun processClickedCell(row: Int, col: Int) {
        val board = findViewById<TableLayout>(R.id.board)
        val cell = (board.getChildAt(row) as TableRow).getChildAt(col) as ImageView
        if (cell.drawable == null) {
            placeStone(cell)
            if (checkWin(row, col)) {
                handleWin()
            }
            else {
                togglePlayer()
            }
        }
        else {
            Toast.makeText(this, "해당 위치에는 돌이 이미 존재합니다.\n다른 위치를 선택하세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun placeStone(cell: ImageView) {
        if (currentPlayer == PLAYER_BLACK) {
            cell.setImageResource(R.drawable.black_stone)
            cell.tag = PLAYER_BLACK
        }
        else {
            cell.setImageResource(R.drawable.white_stone)
            cell.tag = PLAYER_WHITE
        }
    }

    private fun handleWin() {
        Toast.makeText(this, "${currentPlayer}가 승리하였습니다.", Toast.LENGTH_LONG).show()
        initializePlayer()
        initializeBoard()
    }

    private fun togglePlayer() {
        if (currentPlayer == PLAYER_BLACK) {
            currentPlayer = PLAYER_WHITE
        }
        else {
            currentPlayer = PLAYER_BLACK
        }
    }

    private fun checkWin(row: Int, col: Int): Boolean {
        val board = findViewById<TableLayout>(R.id.board)
        val cell = (board.getChildAt(row) as TableRow).getChildAt(col) as ImageView
        val directions = listOf(
            listOf(0 to 1, 0 to -1),
            listOf(1 to 0, -1 to 0),
            listOf(1 to 1, -1 to -1),
            listOf(1 to -1, -1 to 1)
        )
        for (direction in directions) {
            var count = 1
            for ((dr, dc) in direction) {
                count += countStonesInDirection(row, col, dr, dc, cell.tag as Char)
            }
            if (count >= 5) {
                return true
            }
        }
        return false
    }

    private fun countStonesInDirection(row: Int, col: Int, dr: Int, dc: Int, player: Char): Int {
        val board = findViewById<TableLayout>(R.id.board)
        var count = 0
        var r = row + dr
        var c = col + dc

        while (r in 0 until BOARD_SIZE && c in 0 until BOARD_SIZE) {
            val cell = (board.getChildAt(r) as TableRow).getChildAt(c) as ImageView
            if (cell.tag == player) {
                count ++
                r += dr
                c += dc
            }
            else {
                break
            }
        }
        return count
    }
}