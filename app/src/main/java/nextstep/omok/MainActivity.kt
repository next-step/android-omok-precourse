package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children

enum class PlayerTurn {
    BLACK, WHITE;

    fun changeTurn(): PlayerTurn {
        return if (this == BLACK) WHITE else BLACK
    }
}

enum class Stone {
    BLACK, WHITE, EMPTY;
}

class MainActivity : AppCompatActivity() {

    var currentPlayer = PlayerTurn.BLACK
    var boardSize: Int = 15
    var checkedBoard: List<MutableList<Stone>> = MutableList(boardSize) {
        MutableList(boardSize) { Stone.EMPTY }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start()
    }

    private fun start(){
        val imgWhite = findViewById<ImageView>(R.id.img_white)
        val imgBlack = findViewById<ImageView>(R.id.img_black)
        val board = findViewById<TableLayout>(R.id.board)
        val restartBtn = findViewById<Button>(R.id.restart_btn)

        initImgOpacity(imgWhite,imgBlack)

        setBoard(imgWhite,imgBlack,board)

        restartBtn.setOnClickListener {
            init(board)
        }
    }
    private fun initImgOpacity(imgWhite: ImageView, imgBlack: ImageView) {
        imgWhite.alpha = 0.2f
        imgBlack.alpha = 1.0f
    }

    private fun setBoard(imgWhite: ImageView, imgBlack: ImageView,board: TableLayout){
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { idx, view ->
                var row = idx / boardSize
                var col = idx % boardSize

                view.setOnClickListener {
                    placeStone(view, row, col, imgWhite, imgBlack)
                }
            }
    }

    private fun placeStone(
        view: ImageView,
        row: Int,
        col: Int,
        imgWhite: ImageView,
        imgBlack: ImageView
    ) {
        if (!isvalidPositon(row, col)) return

        val stoneResId = when (currentPlayer) {
            PlayerTurn.BLACK -> R.drawable.black_stone
            PlayerTurn.WHITE -> R.drawable.white_stone
        }

        view.setImageResource(stoneResId)

        checkedBoard[row][col] = if (currentPlayer == PlayerTurn.BLACK) Stone.BLACK else Stone.WHITE

        if (checkWin(row, col, checkedBoard)) {
            showResultScreen()
        }else{
            changeTurn(imgWhite, imgBlack)
        }
    }

    private fun isvalidPositon(row: Int,col: Int):Boolean{
        return if (checkedBoard[row][col] != Stone.EMPTY) {
            Toast.makeText(this, "이미 돌이 놓여 있습니다", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun showResultScreen(){
        val resultTextView = findViewById<TextView>(R.id.result_textView)
        val resultLayout = findViewById<ViewGroup>(R.id.result_layout)

        when (currentPlayer){
            PlayerTurn.BLACK -> resultTextView.text = "Black Wins!"
            PlayerTurn.WHITE -> resultTextView.text = "White Wins!"
        }
        resultLayout.visibility = View.VISIBLE
    }

    private fun hideResultScreen(){
        val resultLayout = findViewById<View>(R.id.result_layout)
        resultLayout.visibility = View.INVISIBLE
    }

    private fun changeTurn(imgWhite: ImageView, imgBlack: ImageView) {
        when (currentPlayer) {
            PlayerTurn.BLACK -> {
                imgWhite.alpha = 1.0f
                imgBlack.alpha = 0.2f
            }

            PlayerTurn.WHITE -> {
                imgWhite.alpha = 0.2f
                imgBlack.alpha = 1.0f
            }
        }
        currentPlayer = currentPlayer.changeTurn()

    }

    private fun checkWin(row: Int, col: Int, board: List<MutableList<Stone>>): Boolean {
        val directions = listOf(
            Pair(1, 0),
            Pair(0, 1),
            Pair(1, 1),
            Pair(1, -1)
        )
        val stoneType = board[row][col]
        for ((dx, dy) in directions) {
            var count = 1 + countStone(row, col, dx, dy, stoneType, board)+
                    countStone(row, col, -dx, -dy, stoneType, board)
            if (count >= 5) return true
        }
        return false
    }

    private fun countStone(
        row: Int, col: Int,
        dx: Int, dy: Int, stoneType: Stone,
        board: List<MutableList<Stone>>
    ): Int {
        var count = 0
        var x = row + dx
        var y = col + dy

        while (x in 0 until boardSize
            && y in 0 until boardSize
            && board[x][y] == stoneType
        ) {
            count++
            x += dx
            y += dy
        }
        return count
    }

    private fun init(board:ViewGroup){
        currentPlayer = PlayerTurn.BLACK
        checkedBoard = MutableList(boardSize){
            MutableList(boardSize) { Stone.EMPTY }}
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { it.setImageResource(0) }
        hideResultScreen()
    }




}
