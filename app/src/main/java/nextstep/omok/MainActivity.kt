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
            togglePlayer()
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

    private fun togglePlayer() {
        if (currentPlayer == PLAYER_BLACK) {
            currentPlayer = PLAYER_WHITE
        }
        else {
            currentPlayer = PLAYER_BLACK
        }
    }
}