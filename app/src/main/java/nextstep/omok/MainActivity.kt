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

    private fun initializeBoard(board:TableLayout){
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
        val row = position / boardSize
        val column = position % boardSize

        if (boardState[position] == null) {
            placeStone(view,position)
        } else {
            val toast = Toast.makeText(applicationContext, "이미 돌이 놓인 위치입니다.", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private fun placeStone(view:ImageView,position:Int) {
        if (currentStone == 0) {
            view.setImageResource(R.drawable.black_stone)
            boardState[position] = 0;   //black
            currentStone = 1
        } else {
        view.setImageResource(R.drawable.white_stone)
        boardState[position] = 1;   //white
        currentStone = 0}
    }
}
