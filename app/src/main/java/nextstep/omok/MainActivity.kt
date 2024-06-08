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
            boardState[position] = 0;   //black
            currentStone = 1
        } else {
        view.setImageResource(R.drawable.white_stone)
        boardState[position] = 1;   //white
        currentStone = 0}

        if (boardState.filterNotNull().size >= 3){
            checkWin(position)
        }
    }

    private fun checkWin(position: Int): Boolean {
        return checkDirection(position, -1, 0) || // 가로
               checkDirection(position, 0, -1) || // 세로
               checkDirection(position, -1, -1) || // 왼->오 대각선
               checkDirection(position, -1, 1) // 오->왼 대각선
    }

    private fun checkDirection(position: Int, dRow: Int, dColumn: Int): Boolean {
        val row = position / boardSize
        val column = position % boardSize
        val stone = boardState[position] ?: return false

        val count = (1 until 5).count { i ->
            val newRow = row + i * dRow
            val newColumn = column + i * dColumn
            newRow in 0 until boardSize && newColumn in 0 until boardSize && boardState[newRow * boardSize + newColumn] == stone
        }

        return count >= 4
    }


}
