package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    var isBlackTurn = true  //플레이어1(흑돌) or 플레이어2(백돌) 여부
    val boardState = Array(15) { IntArray(15) {0} }  //보드 상태(2차원 배열), 0: 빈 칸, 1: 흑돌, 2: 백돌
    var gameEnded = false  //게임 종료 여부

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)  //보드
        val restartButton = findViewById<Button>(R.id.restartButton)  //Restart Button

        setBoard(board)  //보드의 각 칸에 클릭 리스너 설정
        /*
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setOnClickListener { view.setImageResource(R.drawable.black_stone) } }*/
    }

    //보드의 각 칸에 클릭 리스너 설정
    fun setBoard(board: TableLayout) {
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
            row.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, view ->
                view.setOnClickListener {
                    onCellClick(view, rowIndex, colIndex)  //칸(셀) 클릭 시 동작
                }
            }
        }
    }

    //칸(셀) 클릭 시 동작 정의
    fun onCellClick(view: ImageView, row: Int, col: Int) {
        if (!gameEnded && boardState[row][col] == 0) {
            placeStone(view, row, col)  //돌 놓기
            handleGameState(row, col)  //게임 상태 처리
        }
    }

    //돌 놓고 보드 상태 업데이트
    fun placeStone(view: ImageView, row: Int, col: Int) {
        val drawable = if (isBlackTurn) R.drawable.black_stone else R.drawable.white_stone
        view.setImageResource(drawable)  //돌 이미지 설정
        boardState[row][col] = if (isBlackTurn) 1 else 2  //보드 상태 업데이트
    }

    //게임 상태
    fun handleGameState(row: Int, col: Int) {
        when {

        }
    }
}
