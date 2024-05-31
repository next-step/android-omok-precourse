package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    // 오목 보드
    private lateinit var board: Array<Array<ImageView?>>
    // 15x15 보드 사이즈
    private val boardSize = 15
    // 현재 플레이어(black or white)
    private var currentPlayer = "black"
    // 좌표 방향 리스트
    private val directions = listOf(
        listOf(1 to 0, -1 to 0),  // 세로
        listOf(0 to 1, 0 to -1),  // 가로
        listOf(1 to 1, -1 to -1), // 대각선 \
        listOf(1 to -1, -1 to 1)  // 대각선 /
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tableLayout = findViewById<TableLayout>(R.id.board)
        board = Array(boardSize) { arrayOfNulls(boardSize) }
        var rowIndex = 0 // 세로
        tableLayout.children.filterIsInstance<TableRow>().forEach { row ->
                var colIndex = 0 // 가로
                row.children.filterIsInstance<ImageView>().forEach { view ->
                        view.setOnClickListener { onStonePlaced(view) } // 클릭 시 돌 놓기
                        view.tag = "$rowIndex,$colIndex"  // 태그에 세로, 가로 설정
                        board[rowIndex][colIndex] = view
                        colIndex++ }
                rowIndex++ }
    }
    // 돌 놓는 함수
    private fun onStonePlaced(view: ImageView) {
        if (view.tag != "black" && view.tag != "white") {  // 아직 돌이 놓이지 않은 경우에만
            val (row, col) = (view.tag as String).split(",").map { it.toInt() }  // 태그에서 좌표 추출
            // 현재 플레이어가 흑돌이면 흑돌, 백돌이면 백돌 놓음
            view.setImageResource(if (currentPlayer == "black") R.drawable.black_stone else R.drawable.white_stone)
            view.tag = currentPlayer  // 현재 플레이어를 태그로 설정
            if (checkWin(row, col)) {   // 승리 조건 확인
                Toast.makeText(this, "$currentPlayer wins!", Toast.LENGTH_LONG).show() // 승리 메시지
                resetBoard() // 보드 초기화
            } else {
                currentPlayer = if (currentPlayer == "black") "white" else "black" // 플레이어 변경
            }
        }
    }




}
