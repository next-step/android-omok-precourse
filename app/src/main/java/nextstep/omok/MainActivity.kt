package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private val game = OmokGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeBoard()
    }

    private fun initializeBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        board.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, cell ->
                        cell.setImageResource(0)
                        cell.tag = null
                        cell.setOnClickListener { onCellClicked(rowIndex, colIndex, cell) }
                    }
            }
    }

    private fun onCellClicked(row: Int, col: Int, cell: ImageView) {
        if (game.placeStone(row, col)) {
            placeStone(cell, game.currentPlayer)
            if (game.checkWin(row, col)) {
                showWinMessage()
                resetBoard()
            } else {
                game.togglePlayer()
            }
        } else {
            showInvalidMoveMessage()
        }
    }

    private fun placeStone(cell: ImageView, player: Char) {
        val resource = if (player == PLAYER_BLACK) R.drawable.black_stone else R.drawable.white_stone
        cell.setImageResource(resource)
        cell.tag = player
    }

    private fun showInvalidMoveMessage() {
        Toast.makeText(this, "해당 위치에는 돌이 이미 존재합니다.\n다른 위치를 선택하세요.", Toast.LENGTH_SHORT).show()
    }

    private fun showWinMessage() {
        Toast.makeText(this, "${game.currentPlayer}가 승리하였습니다.", Toast.LENGTH_LONG).show()
    }

    private fun resetBoard() {
        game.resetGame()
        initializeBoard()
    }
}
