package nextstep.omok

import android.content.Intent
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
        initializeBoard(board)
        Toast.makeText(this, "가위바위보를 해서 이긴 사람이 백돌로 먼저 시작합니다.", Toast.LENGTH_LONG).show()
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
    fun checkWin(row: Int, col: Int): Boolean{
        return checkDirection(row, col, 1, 0) ||  // 수평
                checkDirection(row, col, 0, 1) ||  // 수직
                checkDirection(row, col, 1, 1) ||  // 대각선 \
                checkDirection(row, col, 1, -1)    // 대각선 /
    }
    fun checkDirection(row: Int, col: Int, deltaRow: Int, deltaCol: Int): Boolean {
        var count = 1
        count += countStones(row, col, deltaRow, deltaCol)
        count += countStones(row, col, -deltaRow, -deltaCol)
        return count >= 5
    }

    fun countStones(row: Int, col: Int, deltaRow: Int, deltaCol: Int): Int {
        var count = 0
        var r = row + deltaRow
        var c = col + deltaCol
        while (r in 0 until boardSize && c in 0 until boardSize && boardState[r][c] == currentPlayer) {
            count++
            r += deltaRow
            c += deltaCol
        }
        return count
    }
    fun showWinner(){
        val winnerMessage = if (currentPlayer == "w") "백돌이 5목 이상을 완성하여 승리했습니다!" else "흑돌이 5목 이상을 완성하여 승리했습니다!"
        Toast.makeText(this, winnerMessage, Toast.LENGTH_LONG).show()
        switchPlayer()
    }
}
