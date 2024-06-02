package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private val boardSize = 15
    private lateinit var game: OmokGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        game = OmokGame(boardSize)
        setupBoard()
    }

    private fun setupBoard() {
        val boardView = findViewById<TableLayout>(R.id.board)
        boardView.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                val row = index / boardSize
                val col = index % boardSize
                view.setOnClickListener { handleCellClick(row, col, view) }
            }
    }

    private fun handleCellClick(row: Int, col: Int, view: ImageView) {
        if (game.board[row][col] == 0) {
            if (game.isForbidden(row, col)) {
                Toast.makeText(this, "Forbidden move!", Toast.LENGTH_SHORT).show()
                return
            }
            game.placeStone(row, col)
            view.setImageResource(if (game.currentPlayer == 1) R.drawable.black_stone else R.drawable.white_stone)
            if (game.checkWin(row, col, game.currentPlayer)) {
                showWinner(game.currentPlayer)
            } else {
                game.switchPlayer()
            }
        }
    }

    private fun showWinner(player: Int) {
        val winner = if (player == 1) "Black" else "White"
        Toast.makeText(this, "$winner wins!", Toast.LENGTH_SHORT).show()
    }
}
