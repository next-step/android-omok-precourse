package nextstep.omok

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var board: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBoard()
    }

    private fun setupBoard() {
        val tableLayout = findViewById<TableLayout>(R.id.board)
        board = Board(tableLayout, ::onCellClicked)
        board.initialize()
    }

    private fun onCellClicked(row: Int, col: Int) {
        val result = gameLogic.placeStone(row, col)
        when (result) {
            is GameResult.Success -> board.updateCell(row, col, result.player)
            is GameResult.Win -> handleWin(row, col, result.player)
            is GameResult.Occupied -> showToast("Cell is already occupied")
            is GameResult.Invalid -> showToast("Invalid move")
        }
    }

    private fun handleWin(row: Int, col: Int, player: Player) {
        board.updateCell(row, col, player)
        showToast("${player.name} wins!")
        board.reset()
        gameLogic.reset()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
