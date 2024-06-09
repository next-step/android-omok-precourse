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
        val tag = view.tag as? String //좌표태그를 tag변수에 초기화
        tag?.let {
            val (rowIndex, colIndex) = it.split(",").map { it.toInt() } // ,기준 split하여 좌표값 설정
            if (isEmptyPlace(rowIndex, colIndex)) { //해당 좌표에 돌을 놓을 수 있다면
                if (isblackturn) { //흑돌차례일 때
                    view.setImageResource(R.drawable.black_stone)
                    stoneArray[rowIndex][colIndex] = 1
                } else { //백돌차례일 때
                    view.setImageResource(R.drawable.white_stone)
                    stoneArray[rowIndex][colIndex] = 2
                }
                Log.d("testt", it) //놓은 돌의 좌표 로그 출력
                checkWin(rowIndex, colIndex) //승리조건 판별
                isblackturn = !isblackturn //턴 넘기기
                displayNowTurn(isblackturn) //바뀐 턴 표시하기
            } else { //놓을 수 없는 곳에 클릭했을 때
                Toast.makeText(this, "빈 곳에 놓아주세요", Toast.LENGTH_SHORT).show()
            }
        }
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

            for (i in 1 until 5) {
                val newRow1 = row + i * dx1
                val newCol1 = col + i * dy1
                val newRow2 = row + i * dx2
                val newCol2 = col + i * dy2

                if (checkSameStone(newRow1,newCol1,stone)||checkSameStone(newRow2,newCol2,stone)) { //주변 돌들과 놓은 돌 비교
                    count++
                }
                else break

            }
            Log.d("testt", "Count: $count")

            if (count >= 5) {
                whenWin(stone)
            }
        }
    }

    fun checkSameStone(newRow : Int, newCol : Int, stone : Int):Boolean{
        return (newRow in 0..14 && newCol in 0..14 && stoneArray[newRow][newCol] == stone)
    }

    //게임 종료시 재실행 여부 확인
    fun showEndGameDialog(winner: String) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("게임 종료")
        builder.setMessage("$winner 승리!\n다시 하시겠습니까?")

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
        Toast.makeText(this, "$winner 승리!", Toast.LENGTH_SHORT).show()
        showEndGameDialog(winner)
    }

}

