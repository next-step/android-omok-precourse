package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val blackPlayer = Player("black")
        val whitePlayer = Player("white")
        val omok = Game(blackPlayer, whitePlayer)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setOnClickListener {
                val row = view.getParent() as TableRow
                val indexRow: Int = board.indexOfChild(row)
                val indexCol: Int = row.indexOfChild(view)

                omok.currentPlayer.putStone(view)
                omok.recordBoard(indexRow, indexCol)
                omok.changeTurn()
            } }
    }
}

class Game(val blackPlayer : Player, val whitePlayer : Player) {
    val board = Array(15,{ Array(15,{"blank"})})
    var currentPlayer : Player = blackPlayer // 시작은 흑돌먼저
    fun changeTurn() {
        if(currentPlayer == blackPlayer) {
            currentPlayer = whitePlayer
        } else {
            currentPlayer = blackPlayer
        }
    }

    fun recordBoard(row : Int, col : Int) {
        Log.d("testt", "index : "+ row + "," + col)
        board[row][col] = currentPlayer.color
    }

    fun gameOver(view: TextView, currentPlayer: Player, tableLayout: TableLayout) {
        view.text = "게임 종료 " + currentPlayer.color + " 승리"
        tableLayout.isClickable = false
    }
}

class Player(
    val color : String
) {
    fun putStone(view : ImageView) {
        if(color == "black") {
            view.setImageResource(R.drawable.black_stone)
        } else {
            view.setImageResource(R.drawable.white_stone)
        }
        view.isClickable = false // 이미 돌을 둔 칸은 못두도록
    }
}
