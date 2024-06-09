package nextstep.omok

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    var isblackturn: Boolean = true
    lateinit var nowTurnDisplay: TextView
    lateinit var reset: Button
    var stoneArray = Array(15) { IntArray(15) { 0 } }
    var fullBoard = 225
    var stoneCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reset = findViewById(R.id.reset)
        nowTurnDisplay = findViewById(R.id.nowTurnDisplay)
        val board = findViewById<TableLayout>(R.id.board)

        var rowIndex = 0
        board.children
            .filterIsInstance<TableRow>()
            .forEach { tableRow ->
                var colIndex = 0
                tableRow.children.filterIsInstance<ImageView>().forEach { view ->
                    view.setOnClickListener { onStonePlace(view) }
                    view.tag = "$rowIndex,$colIndex"
                    colIndex++
                }
                rowIndex++
            }

        reset.setOnClickListener { resetGame(board) }

    }

    fun isEmptyPlace(row: Int, col: Int): Boolean {
        return row in 0..14 && col in 0..14 && stoneArray[row][col] == 0
    }

    fun onStonePlace(view: ImageView) {
        val tag = view.tag as? String
        tag?.let {
            val (rowIndex, colIndex) = it.split(",").map { it.toInt() }
            if (isEmptyPlace(rowIndex, colIndex)) {
                placeStone(view, rowIndex, colIndex)
                checkFullBoard()
                switchTurn()
            } else {
                Toast.makeText(this, "빈 곳에 놓아주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun placeStone(view: ImageView, rowIndex: Int, colIndex: Int) {
        if (isblackturn) {
            view.setImageResource(R.drawable.black_stone)
            stoneArray[rowIndex][colIndex] = 1
        } else {
            view.setImageResource(R.drawable.white_stone)
            stoneArray[rowIndex][colIndex] = 2
        }
//        Log.d("testt", "$rowIndex,$colIndex") //디버깅 용
        checkWin(rowIndex, colIndex)
//        fullBoard-- //
//        stoneCount++
//        Log.d("fullBoard", "${fullBoard.toString()} ${stoneCount.toString()}") 디버깅 용
    }

    fun switchTurn() {
        isblackturn = !isblackturn
        displayNowTurn(isblackturn)
    }

    fun checkFullBoard() {
        if (fullBoard == 0) showEndGameDialog("무승부")
    }

    //차례를 표시하는 TextView
    fun displayNowTurn(isblackturn: Boolean) {
        if (isblackturn) {
            nowTurnDisplay.text = "현재 차례\nBLACK"
            nowTurnDisplay.setBackgroundColor(Color.BLACK)
            nowTurnDisplay.setTextColor(Color.WHITE)
        } else {
            nowTurnDisplay.text = "현재 차례\nWHITE"
            nowTurnDisplay.setBackgroundColor(Color.WHITE)
            nowTurnDisplay.setTextColor(Color.BLACK)
        }
    }

    //게임리셋 버튼
    fun resetGame(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setImageResource(0) } //각 이미지뷰 리소스를 0으로 설정

        stoneArray = Array(15) { IntArray(15) { 0 } } //좌표 데이터도 초기화
        isblackturn = true //흑돌 선공 결정
        displayNowTurn(isblackturn) //현재 차례 흑돌 설정
        fullBoard = 225
        stoneCount = 0
    }

    //승리판별
    fun checkWin(row: Int, col: Int) {
        val directions = listOf(
            listOf(1 to 0, -1 to 0),   // 수평 방향
            listOf(0 to 1, 0 to -1),   // 수직 방향
            listOf(1 to 1, -1 to -1),  // 대각선 방향 (오른쪽 아래로)
            listOf(1 to -1, -1 to 1)   // 대각선 방향 (오른쪽 위로)
        )
        val stone = stoneArray[row][col] // 흑,백 판별 흑이면 1 백이면 2
        for (direction in directions) {
            val (dx1, dy1) = direction[0]
            val (dx2, dy2) = direction[1]
            var count = 1

            // 첫 번째 방향으로 돌 카운트
            count = countStone(row,dx1,col,dy1,stone,count)
            // 두 번째 방향으로 돌 카운트
            count = countStone(row,dx2,col,dy2,stone,count)

            Log.d("testt", "Count: $count")

            if (count >= 5) {
                whenWin(stone)
            }
        }
    }

    fun checkSameStone(newRow : Int, newCol : Int, stone : Int):Boolean{
        return (newRow in 0..14 && newCol in 0..14 && stoneArray[newRow][newCol] == stone)
    }

    fun countStone (row: Int, dx : Int, col : Int, dy : Int, stone : Int, count : Int) : Int{

        var result = count

        for (i in 1 until 5) {
            val newRow1 = row + i * dx
            val newCol1 = col + i * dy

            if (checkSameStone(newRow1, newCol1, stone)) {
                result++
            } else {
                break
            }
        }
        return result
    }


    //게임 종료시 재실행 여부 확인
    fun showEndGameDialog(winner: String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("게임 종료")
        if(winner.equals("무승부")){
            builder.setMessage("무승부\n다시 하시겠습니까?")
        }
        else builder.setMessage("$winner 승리!\n다시 하시겠습니까?")

        builder.setPositiveButton("다시하기") { dialog, _ ->
            resetGame(findViewById(R.id.board))
            dialog.dismiss()
        }

        builder.setNegativeButton("종료하기") { dialog, _ ->
            finish()
        }

        builder.setCancelable(false)
        builder.show()
    }

    //승리 Toast메세지 출력
    fun whenWin(stone : Int){
        val winner = if (stone == 1) "흑돌" else "백돌"
        showEndGameDialog(winner)
    }

}

