package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children


class MainActivity : AppCompatActivity() {

    private var currentStone = 0  //0:black, 1:white

    private val boardSize = 15
    private val boardState = Array<Int?>(boardSize * boardSize) { null }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        initializeBoard(board)
    }

    private fun initializeBoard(board: TableLayout){
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                view.setOnClickListener { isPositionEmpty(view, index) }
            }
    }

    private fun isPositionEmpty(view: ImageView, position: Int) {
        if (boardState[position] == null)  placeStone(view,position)
        else showToast("이미 돌이 놓인 곳입니다.\n 다른 위치를 선택하세요.")
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun placeStone(view: ImageView,position: Int) {
        if (currentStone == 0) {
            view.setImageResource(R.drawable.black_stone)
            boardState[position] = 0   //black
            currentStone = 1
        } else {
        view.setImageResource(R.drawable.white_stone)
        boardState[position] = 1   //white
        currentStone = 0}

        checkState(position)

        checkDiagonal(position)
    }

    private fun checkState(position: Int){
        val row = position / boardSize
        val column = position % boardSize
        val stone = boardState[position]?: return

        checkVertical(row,column,stone)
        checkHorizontal(row,column,stone)
    }
    private fun checkVertical(row: Int, column: Int, stone: Int){
        val topCount = (row - 1 downTo 0).takeWhile { boardState[it * boardSize + column] == stone }.count()
        val bottomCount = (row + 1 until boardSize).takeWhile { boardState[it * boardSize + column] == stone }.count()

        val count = topCount + bottomCount

        if (count>=4 && stone==0) showToast("흑돌 승리!")
        else if (count>=4 && stone==1) showToast("백돌 승리!")
    }

    private fun checkHorizontal(row: Int, column: Int, stone: Int){

        val leftCount = (column - 1 downTo 0).takeWhile { boardState[row * boardSize + it] == stone }.count()
        val rightCount = (column + 1 until boardSize).takeWhile { boardState[row * boardSize + it] == stone }.count()

        val count = leftCount + rightCount

        if (count>=4 && stone==0) showToast("흑돌 승리!")
        else if (count>=4 && stone==1) showToast("백돌 승리!")
    }

    private fun checkDiagonal(position: Int){
        val row = position / boardSize
        val column = position % boardSize
        val stone = boardState[position]

        // 왼->오 대각선
        val leftTopCount = (1..minOf(row, column)).takeWhile { boardState[(row - it) * boardSize + (column - it)] == stone }.count()
        val rightBottomCount = (1..minOf(boardSize - row - 1, boardSize - column - 1)).takeWhile { boardState[(row + it) * boardSize + (column + it)] == stone }.count()

        // 오->왼 대각선
        val rightTopCount = (1..minOf(row, boardSize - column - 1)).takeWhile { boardState[(row - it) * boardSize + (column + it)] == stone }.count()
        val leftBottomCount = (1..minOf(boardSize - row - 1, column)).takeWhile { boardState[(row + it) * boardSize + (column - it)] == stone }.count()

        val countd1 = leftTopCount + rightBottomCount
        val countd2 = rightTopCount + leftBottomCount

        if (countd1>=4 || countd2>=4 && stone==0) showToast("흑돌 승리!")
        else if (countd1>=4 || countd2>=4 && stone==1) showToast("백돌 승리!")
    }

}
