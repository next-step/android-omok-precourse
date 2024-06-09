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
import androidx.core.view.get

class MainActivity : AppCompatActivity() {

    var isblackturn : Boolean = true
    lateinit var nowTurnDisplay : TextView
    lateinit var  reset : Button
    lateinit var imageViewArray : Array<Array<ImageView?>>
    var stoneAraay = Array(15){ IntArray(15){0} }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nowTurnDisplay = findViewById<TextView>(R.id.nowTurnDisplay)
        reset = findViewById<Button>(R.id.reset)

        val board = findViewById<TableLayout>(R.id.board)
        var rowIndex =0 //세로

        board
            .children //모든 자식 요소 가져오기
            .filterIsInstance<TableRow>() //TableRow인 요소만 필터링
            .forEach { tableRow ->
                var colIndex =0 //가로
                tableRow.children.filterIsInstance<ImageView>().forEach { view ->
                    view.setOnClickListener { onStonePlace(board,view) }
                    view.tag = "$rowIndex,$colIndex" //태그 달기
                    colIndex++
                }
                rowIndex++
            }

        val ROWS = 15 // 행의 개수
        val COLS = 15 // 열의 개수

        imageViewArray = Array(ROWS) { row ->
            Array<ImageView?>(COLS) { col ->
                // 각 셀의 좌표를 찾기 위해 빈 값으로 초기화
                var imageView: ImageView? = null

                // 각 TableRow에서 ImageView를 찾아서 좌표에 할당
                val tableRow = board.getChildAt(row) as? TableRow
                tableRow?.let { rowLayout ->
                    imageView = rowLayout.getChildAt(col) as? ImageView
                }
                // 좌표 할당된 ImageView 반환
                imageView
            }
        }



        reset.setOnClickListener { resetGame(board) }
    }

    //돌 놓을 때 빈 공간인지 확인하는 함수
    fun isEmptyPlace(row: Int, col: Int): Boolean {
        return imageViewArray[row][col]?.drawable == null
    }

    //클릭해서 돌을 놓을 때
    fun onStonePlace(board: TableLayout, view: ImageView){

        val tag = view.tag as? String
        tag?.let {
            val (rowIndex, colIndex) = it.split(",").map { it.toInt() }
            if (isEmptyPlace(rowIndex, colIndex)) {
                if (isblackturn) { //흑돌 놓기
                    view.setImageResource(R.drawable.black_stone)
                    stoneAraay[rowIndex][colIndex] = 1
                }
                else { //백돌 놓기
                    view.setImageResource(R.drawable.white_stone)
                    stoneAraay[rowIndex][colIndex] = 2
                }
                Log.d("testt", it) //놓은 돌의 좌표값

                checkSameStone(rowIndex, colIndex) //주변 돌들의 일치 여부 체크
                isblackturn = !isblackturn //턴바꾸기
                displayNowTurn(isblackturn)
            } else {
                Toast.makeText(this, "빈 곳에 놓아주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //현재 차례를 보여주는 함수
    fun displayNowTurn(isblackturn : Boolean){
        if(isblackturn){
            nowTurnDisplay.text="현재 차례\nBLACK"
            nowTurnDisplay.setBackgroundColor(Color.BLACK)
            nowTurnDisplay.setTextColor(Color.WHITE)
        }
        else{
            nowTurnDisplay.text="현재 차례\nWHITE"
            nowTurnDisplay.setBackgroundColor(Color.WHITE)
            nowTurnDisplay.setTextColor(Color.BLACK)
        }
    }

    //초기화 버튼
    fun resetGame(board : TableLayout){
        board
            .children //모든 자식 요소 가져오기
            .filterIsInstance<TableRow>() //TableRow인 요소만 필터링
            .flatMap { it.children } //각 TableRow의 자식 요소를 평탄화
            .filterIsInstance<ImageView>() //ImageView인 요소만 필터링
            .forEach { view -> view.setImageResource(0) } // 리소스 제거

        isblackturn = true
        displayNowTurn(isblackturn)
    }

    //돌을 놓고 연속된 돌을 확인하는 함수
    fun checkSameStone(row: Int, col: Int) {
        val directions = listOf(
            listOf(1 to 0, -1 to 0),   // 수평 방향
            listOf(0 to 1, 0 to -1),   // 수직 방향
            listOf(1 to 1, -1 to -1),  // 대각선 방향 (오른쪽 아래로)
            listOf(1 to -1, -1 to 1)   // 대각선 방향 (오른쪽 위로)
        )
        var count = 1
        for (direction in directions) {
            val (dx1, dy1) = direction[0]
            val (dx2, dy2) = direction[1]


            val stone = stoneAraay[row][col] //좌표값으로 흑/백 판별

            // 현재 돌 위치를 기준으로 주변 돌을 검사하여 연속된 돌의 개수를 세기
            for (i in 1 until 5) {
                val newRow1 = row + i * dx1
                val newCol1 = col + i * dy1
                val newRow2 = row + i * dx2
                val newCol2 = col + i * dy2

                var newstone1 = stoneAraay[newRow1][newCol1]
                var newstone2 = stoneAraay[newRow2][newCol2]

                if ((stone == newstone1) || (stone == newstone2))  { //놓은 돌 기준으로 양방향으로 뻗어나가며 비교
                    count++
                } else {
                    break
                }

            }
            Log.d("testt",count.toString())

            if (count >= 5) {
                val winner = if (isblackturn) "흑돌" else "백돌"
                Toast.makeText(this, "$winner 승리!", Toast.LENGTH_SHORT).show()
                // 게임 종료 또는 다른 필요한 로직을 추가할 수 있습니다.
            }
        }
    }



}

