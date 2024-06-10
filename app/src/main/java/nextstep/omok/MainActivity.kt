package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import android.widget.Toast
import android.app.AlertDialog

class MainActivity : AppCompatActivity() {

    // 현재 턴을 나타내는 변수: true이면 흑돌의 턴, false이면 백돌의 턴
    private var isBlackTurn: Boolean = true

    // 오목판의 상태를 저장하는 2차원 배열
    private lateinit var boardState: Array<IntArray>
    private val boardSize = 15 // 오목판 크기 (15x15)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeUI()  // UI 시작
    }

    // UI 초기화
    private fun initializeUI() {
        // 오목판 상태 초기화
        boardState = Array(boardSize) { IntArray(boardSize) }

        stoneImageView { index, view ->
            view.setOnClickListener {
                placeStone(index, view)
            }
        }
    }

    // 흑백돌 이미지
    private fun stoneImageView(block: (Int, ImageView) -> Unit) {
        val board = findViewById<TableLayout>(R.id.board)
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                block(index, view)
            }
    }

    // 돌 놓기
    private fun placeStone(index: Int, view: ImageView) {
        // 이미 돌이 놓여 있는 경우를 처리
        if (view.drawable != null) {
            return
        }

        // 현재 턴에 따라 돌을 놓고, 턴을 변경
        val row = index / boardSize
        val col = index % boardSize
        if (isBlackTurn) {
            view.setImageResource(R.drawable.black_stone)
            boardState[row][col] = 1
        } else {
            view.setImageResource(R.drawable.white_stone)
            boardState[row][col] = 2
        }

        // 승리 조건 확인
        if (checkWin(row, col)) {
            // 승리 처리 (예: 메시지 표시)
            val winner = if (isBlackTurn) "Black" else "White"
            Toast.makeText(this, "$winner wins!", Toast.LENGTH_LONG).show()
        }

        isBlackTurn = !isBlackTurn  // 턴을 변경
    }

    // 승리 조건 확인
    private fun checkWin(row: Int, col: Int): Boolean {
        val currentPlayer = boardState[row][col]
        if ((checkDirection(row, col, 1, 0, currentPlayer) + checkDirection(row, col, -1, 0, currentPlayer) >= 4) || // 가로
            (checkDirection(row, col, 0, 1, currentPlayer) + checkDirection(row, col, 0, -1, currentPlayer) >= 4) || // 세로
            (checkDirection(row, col, 1, 1, currentPlayer) + checkDirection(row, col, -1, -1, currentPlayer) >= 4) || // 대각선 \
            (checkDirection(row, col, 1, -1, currentPlayer) + checkDirection(row, col, -1, 1, currentPlayer) >= 4)) {   // 대각선 /
            showRestartDialog()
            return true
        }
        return false
    }

    // 특정 방향으로 연속된 돌의 개수를 세는 함수
    private fun checkDirection(row: Int, col: Int, dRow: Int, dCol: Int, player: Int): Int {
        var count = 0
        var r = row + dRow
        var c = col + dCol
        while (r in 0 until boardSize && c in 0 until boardSize && boardState[r][c] == player) {
            count++
            r += dRow
            c += dCol
        }
        return count
    }

    // 게임 재시작
    private fun restartGame() {
        // 보드의 모든 이미지 초기화
        clearBoard()
        // 게임 초기화
        isBlackTurn = true
        boardState = Array(boardSize) { IntArray(boardSize) }

    }

    // 보드의 모든 이미지 초기화
    private fun clearBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        for (row in 0 until boardSize) {
            val tableRow = board.getChildAt(row) as TableRow
            for (col in 0 until boardSize) {
                val imageView = tableRow.getChildAt(col) as ImageView
                imageView.setImageDrawable(null)
            }
        }
    }

    // 게임 다시 시작 다이얼로그 표시
    private fun showRestartDialog() {
        AlertDialog.Builder(this)
            .setTitle("게임 종료")
            .setMessage("게임을 다시 시작하시겠습니까?")
            .setPositiveButton("예") { _, _ ->
                restartGame()
            }
            .setNegativeButton("아니오") { _, _ ->
                finish()
            }
            .show()
    }
}
