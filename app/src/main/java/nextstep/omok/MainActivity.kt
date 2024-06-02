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
        setRestartButton(restartButton, board)  //Restart Button 클릭 리스너 설정
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
                view.setOnClickListener { onCellClick(view, rowIndex, colIndex) }  //칸(셀) 클릭 시 동작
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
            checkWin(row, col) -> endGame(if (isBlackTurn) "플레이어1(흑돌)" else "플레이어2(백돌)")  //승리 조건 확인
            isBoardFull() -> endGame("무승부")  //무승부 조건 확인
            else -> isBlackTurn = !isBlackTurn  //플레이어 순서 변경
        }
    }

    //게임 종료
    fun endGame(result: String) {
        gameEnded = true
        Toast.makeText(this, "$result 승리", Toast.LENGTH_LONG).show()
    }

    //승리 조건 확인
    fun checkWin(row: Int, col: Int): Boolean {
        val player = boardState[row][col]
        return (checkDirection(row, col, 1, 0, player) >= 5 || // 가로 방향
                checkDirection(row, col, 0, 1, player) >= 5 || // 세로 방향
                checkDirection(row, col, 1, 1, player) >= 5 || // 대각선(\) 방향
                checkDirection(row, col, 1, -1, player) >= 5)  // 대각선(/) 방향
    }

    //특정 방향으로 연속된 돌 개수 세기
    fun checkDirection(row: Int, col: Int, dRow: Int, dCol: Int, player: Int): Int {
        return (1 + countStones(row, col, dRow, dCol, player) + countStones(row, col, -dRow, -dCol, player))
    }

    fun countStones(row: Int, col: Int, dRow: Int, dCol: Int, player: Int): Int {
        var count = 0
        var i = 1
        while (true) {
            val newRow = row + i * dRow
            val newCol = col + i * dCol
            if (newRow in 0..14 && newCol in 0..14 && boardState[newRow][newCol] == player) {
                count++
            } else break
            i++
        }
        return count
    }

    //무승부 조건 확인
    fun isBoardFull(): Boolean {
        return boardState.all { row -> row.all { it != 0 } }
    }

    //Restart Button 클릭 리스너 설정
    fun setRestartButton(restartButton: Button, board: TableLayout) {
        restartButton.setOnClickListener {
            resetBoard(board)
        }
    }

    //보드 초기화
    fun resetBoard(board: TableLayout) {
        boardState.forEach { row -> row.fill(0) } //보드 상태 초기화
        board.children.filterIsInstance<TableRow>().forEach { row ->
            row.children.filterIsInstance<ImageView>().forEach { view ->
                view.setImageDrawable(null) // 모든 칸(셀) 비움
            }
        }
        isBlackTurn = true // 플레이어1(흑돌)부터 시작
        gameEnded = false // 게임 종료 상태 초기화
    }
}
