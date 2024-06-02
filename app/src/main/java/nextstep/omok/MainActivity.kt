package nextstep.omok

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    // 오목 보드
    lateinit var board: Array<Array<ImageView?>>
    // 15x15 보드 사이즈
    private val boardSize = 15
    // 현재 플레이어(black or white)
    private var currentPlayer = "흑돌"
    // 좌표 방향 리스트
    private val directions = listOf(
        listOf(1 to 0, -1 to 0),  // 세로
        listOf(0 to 1, 0 to -1),  // 가로
        listOf(1 to 1, -1 to -1), // 대각선 \
        listOf(1 to -1, -1 to 1)  // 대각선 /
    )
    //현재 플레이어를 나타낼 텍스트뷰
    private lateinit var currentPlayerTextView:TextView
    //초기화 버튼
    private lateinit var resetButton: TextView
    // 흑돌 승리 카운트
    private var blackWinCount = 0
    // 백돌 승리 카운트
    private var whiteWinCount = 0
    // 흑돌 승리 텍스트뷰
    private lateinit var blackWinTextView: TextView
    // 백돌 승리 텍스트뷰
    private lateinit var whiteWinTextView: TextView
    // 돌을 놓을 수 있는 최대값
    private var fullBoard = 225
    // 앱 실행 시
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        board = Array(boardSize) { arrayOfNulls(boardSize) }
        val tableLayout = tableLayout()
        currentPlayerTextView = findViewById<TextView>(R.id.currentPlayerText)
        resetButton = findViewById<TextView>(R.id.reset_button)
        resetButton.setOnClickListener { resetBoard() }
        blackWinTextView = findViewById<TextView>(R.id.blackWinCount)
        whiteWinTextView = findViewById<TextView>(R.id.whiteWinCount)
    }
    // 보드 생성
    private fun tableLayout(): TableLayout {
        val tableLayout = findViewById<TableLayout>(R.id.board)
        var rowIndex = 0 // 세로
        tableLayout.children.filterIsInstance<TableRow>().forEach { row ->
            var colIndex = 0 // 가로
            row.children.filterIsInstance<ImageView>().forEach { view ->
                view.setOnClickListener { onStonePlaced(view) } // 클릭 시 돌 놓기
                view.tag = "$rowIndex,$colIndex"  // 태그에 세로, 가로 설정
                board[rowIndex][colIndex] = view
                colIndex++
            }
            rowIndex++
        }
        return tableLayout
    }
    // 돌 놓는 함수
    fun onStonePlaced(view: ImageView) {
        if (view.tag != "흑돌" && view.tag != "백돌") {  // 아직 돌이 놓이지 않은 경우에만
            val (row, col) = (view.tag as String).split(",").map { it.toInt() }  // 태그에서 좌표 추출
            view.setImageResource(if (currentPlayer == "흑돌") R.drawable.black_stone else R.drawable.white_stone)// 현재 플레이어가 흑돌이면 흑돌, 백돌이면 백돌 놓음
            view.tag = currentPlayer  // 현재 플레이어를 태그로 설정
            fullBoard-- // 남은 칸 개수 감소
            viewPost(view, row, col)
        }
    }
    // 좌표에 돌이 놓인 후
    private fun viewPost(view: ImageView, row: Int, col: Int) {
        view.post {
            if (checkWin(row, col)) AfterWin() // 승리 조건 확인 후 승리 이후 행동
            else if (fullBoard == 0) fullBoardCheck() // 보드가 모두 찼을 경우
            else {
                currentPlayer = if (currentPlayer == "흑돌") "백돌" else "흑돌" // 플레이어 변경
                currentPlayerTextView.text = currentPlayer // 플레이어 변경 표시
            }
        }
    }
    // 승리 이후 행동.
    // 승리 메시지를 토스트 한 다음 2초 후 보드 초기화
    // 승리 카운트 증가
    private fun AfterWin() {
        Toast.makeText(this, "$currentPlayer 승리!", Toast.LENGTH_LONG).show() // 승리 메시지
        Handler(Looper.getMainLooper()).postDelayed({ resetBoard() }, 2000) // 2초 지연 후 보드 초기화
        if (currentPlayer == "흑돌") {
            blackWinCount++
            blackWinTextView.text = blackWinCount.toString()
        } else {
            whiteWinCount++
            whiteWinTextView.text = whiteWinCount.toString()
        }
    }
    // 보드가 모두 찼을 경우
    // 무승부 메시지를 토스트 한 다음 2초 후 보드 초기화
    private fun fullBoardCheck() {
        Toast.makeText(this, "무승부 입니다.", Toast.LENGTH_LONG).show()
        Handler(Looper.getMainLooper()).postDelayed({ resetBoard() }, 2000) // 2초 지연 후 보드 초기화
        if (currentPlayer == "흑돌") {
            blackWinTextView.text = blackWinCount.toString()
        } else {
            whiteWinTextView.text = whiteWinCount.toString()
        }
    }
    // 승리 조건 확인
    fun checkWin(row: Int, col: Int): Boolean {
        for (direction in directions) { // 가로, 세로, 대각선 방향으로 연속되어 있는지 확인.
            var count = 1 // 돌의 연속 개수
            for ((dx, dy) in direction) {
                val x = row
                val y = col
                count = checkDirection(x, y, dx, dy, count) // 각 방향별 연속 개수 확인
            }
            if (count >= 5) return true
        }
        return false
    }
    // 각 방향별 연속 개수 확인
    private fun checkDirection(row: Int, col: Int, dx: Int, dy: Int, count: Int): Int {
        var x = row // 세로
        var y = col // 가로
        var count = count // 연속 개수
        while (true) {
            x += dx // 좌표 이동
            y += dy // 좌표 이동
            // 보드 범위 벗어 나거나 현재 플레이어 돌과 다른 경우 break
            if (x < 0 || y < 0 || x >= boardSize || y >= boardSize || !isCurrentPlayerStone(x, y)) {
                break
            }
            count++
        }
        return count
    }
    // 현재 플레이어 돌과 같은지 확인
    private fun isCurrentPlayerStone(row: Int, col: Int): Boolean {
        val imageView = board[row][col]
        return imageView?.tag == currentPlayer
    }
    // 보드 초기화
    fun resetBoard() {
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                board[i][j]?.apply {
                    setImageDrawable(null) // 보드 안에 있는 돌들을 다 지움
                    tag = "$i,$j" // 태그에 세로, 가로 설정
                }
            }
        }
        currentPlayer = "흑돌" // 플레이어 초기화
        currentPlayerTextView.text = currentPlayer // 플레이어 변경 표시
        fullBoard = 225
    }
}
