package nextstep.omok

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private var isBlackTurn = true
    private lateinit var board: Array<Array<ImageView>>
    private val boardSize = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
    }

    private fun setView() {
        board = Array(boardSize) {Array(boardSize) { ImageView(this) } } // board라는 2차원 배열 생성
        val tableLayout = findViewById<TableLayout>(R.id.board)

        for (i in 0 until boardSize) { // 각 셀에 접근, 행에 접근
            val tableRow = tableLayout.getChildAt(i) as TableRow // i번째 자식 뷰 반환, tableRow로 캐스팅
            for (j in 0 until boardSize) { // 각 열에 접근
                val imageView = tableRow.getChildAt(j) as ImageView // j번째 자식 뷰 반환, imageView로 캐스팅
                board[i][j] = imageView
                imageView.setOnClickListener { putStoneInTurn(i, j) }
            }
        }
    }

    private fun putStoneInTurn(row: Int, col: Int) {
        if (board[row][col].tag == null) { // 셀이 비어 있고
            if (isBlackTurn) { // 흑돌 차례면
                board[row][col].setImageResource(R.drawable.black_stone) // 흑돌을 놓는다
            } else { // 백돌 차례면
                board[row][col].setImageResource(R.drawable.white_stone) // 백돌을 놓는다
            }
            board[row][col].tag = isBlackTurn // 흑돌이면 true, 백돌이면 false
            isBlackTurn = !isBlackTurn // 차례 변경
        }
        if (checkFiveLine(row, col)) {
            endGame()
            return
        }
    }

    private fun checkFiveLine(row: Int, col: Int): Boolean { // 전체적으로 체크하는 함수
        val color = board[row][col].tag as Boolean
        return checkHorizontal(row, color) || checkVertical(col, color) ||
                checkDiagonal(row, col, color) || checkReverseDiagonal(row, col, color) // 하나만 true여도 true
    }

    private fun checkHorizontal(row: Int, color: Boolean): Boolean { // 가로 체크
        var cnt = 0
        for (col in 0 until boardSize) { // 해당 셀의 행 검사
            if (board[row][col].tag == color) { // 색깔이 같으면
                cnt++ // 카운트 추가
                if (cnt == 5) return true
            } else {
                cnt = 0
            }
        }
        return false
    }

    private fun checkVertical(col: Int, color: Boolean): Boolean { // 세로 체크
        var cnt = 0
        for (row in 0 until boardSize) {
            if (board[row][col].tag == color) {
                cnt++
                if (cnt == 5) return true
            } else {
                cnt = 0
            }
        }
        return false
    }

    private fun checkDiagonal (row: Int, col: Int, color: Boolean): Boolean {
        var cnt = 0
        var i = row
        var j = col
        while (i > 0 && j > 0) { // 대각선 시작점으로 이동
            i--
            j--
        }
        while (i < boardSize && j < boardSize) {
            if (board[i][j].tag == color) {
                cnt++
                if (cnt == 5) return true
            } else {
                cnt = 0
            }
            i++
            j++
        }
        return false
    }
    private fun checkReverseDiagonal (row: Int, col: Int, color: Boolean): Boolean {
        var cnt = 0
        var i = row
        var j = col
        while (i > 0 && j < boardSize - 1) {
            i--
            j++
        }
        while (i < boardSize && j >= 0) {
            if (board[i][j].tag == color) {
                cnt++
                if (cnt == 5) return true
            } else {
                cnt = 0
            }
            i++
            j--
        }
        return false
    }
    private fun endGame() {
        val winner = if (isBlackTurn) "White" else "Black"
        // 돌을 놓고 턴이 바뀐 뒤 게임이 끝나기 때문에 백돌 차례면 흑돌 승이 됨
        Toast.makeText(this, "$winner player win!", Toast.LENGTH_LONG).show()
        
    }

}
