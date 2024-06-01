package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    val boardSize = 15
    val boardState = Array(boardSize) { Array(boardSize) { "" } }
    var currentPlayer = "w"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
    }
    fun initializeBoard(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener { onCellClicked(view, index) }
            }
    }

    fun onCellClicked(view: ImageView, index: Int) {
        val row = index / boardSize
        val col = index % boardSize
        if (boardState[row][col].isEmpty()) {
            boardState[row][col] = currentPlayer
            view.setImageResource(if (currentPlayer == "w") (R.drawable.white_stone) else (R.drawable.black_stone))
            if (checkWin(row, col)) {
                showWinner()
            } else {
                switchPlayer()
            }
        } else {
            Toast.makeText(this, "이곳에는 놓을 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == "w") "b" else "w"
    }
    fun checkWin(): {

    }
    fun showWinner(): {
    }
}
