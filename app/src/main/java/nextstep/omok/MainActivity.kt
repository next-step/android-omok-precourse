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
        var gameOver : Boolean = false

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
                gameOver = omok.checkOmok(indexRow, indexCol) // 항상 recordBoard 다음에 와야됨
                omok.changeTurn()
            } }
    }
}

class Game(val blackPlayer : Player, val whitePlayer : Player) {
    val board = Array(15,{ Array(15,{"blank"})})
    var currentPlayer : Player = blackPlayer // 시작은 흑돌 먼저
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

    fun checkOmok(row : Int, col : Int) : Boolean {
        if(checkLeftToRight(row, currentPlayer.color)){
            Log.d("testt", "game over")
            return true
        }
        if(checkTopToBottom(col, currentPlayer.color)){
            Log.d("testt", "game over")
            return true
        }
        if(row > col) {
            if(checkTopLeftToBottomRightBigRow(row - col, currentPlayer.color)){
                Log.d("testt", "game over")
                return true
            }
        } else {
            if(checkTopLeftToBottomRightBigCol(col - row, currentPlayer.color)){
                Log.d("testt", "game over")
                return true
            }
        }
        if(row + col < 15) {
            if(checkTopRightToBottomLeftUnder15(row + col, currentPlayer.color)){
                Log.d("testt", "game over")
                return true
            }
        } else {
            if(checkTopRightToBottomLeftOver15(row + col, currentPlayer.color)){
                Log.d("testt", "game over")
                return true
            }
        }
        return false
    }

    fun checkLeftToRight(row : Int, stone : String) : Boolean {
        var cnt : Int = 0
        for(i in 0..14) {
            if(board[row][i] == stone) cnt++
            else cnt = 0
            if(cnt == 5) return true
        }
        return false
    }

    fun checkTopToBottom(col : Int, stone : String) : Boolean {
        var cnt : Int = 0
        for(i in 0..14) {
            if(board[i][col] == stone) cnt++
            else cnt = 0
            if(cnt == 5) return true
        }
        return false
    }

    fun checkTopLeftToBottomRightBigRow(rowMinusCol : Int, stone : String) : Boolean {
        var cnt : Int = 0
        for(i in 0..14) {
            if(rowMinusCol + i > 14) break
            if(board[rowMinusCol + i][0 + i] == stone) cnt++
            else cnt = 0
            if(cnt == 5) return true
        }
        return false
    }

    fun checkTopLeftToBottomRightBigCol(colMinusRow : Int, stone : String) : Boolean {
        var cnt : Int = 0
        for(i in 0..14) {
            if(colMinusRow + i > 14) break
            if(board[0 + i][colMinusRow + i] == stone) cnt++
            else cnt = 0
            if(cnt == 5) return true
        }
        return false
    }

    fun checkTopRightToBottomLeftUnder15(colPlusRow : Int, stone : String) : Boolean {
        var cnt : Int = 0
        for(i in 0..14) {
            if(colPlusRow - i < 0) break
            if(board[colPlusRow - i][0 + i] == stone) cnt++
            else cnt = 0
            if(cnt == 5) return true
        }
        return false
    }

    fun checkTopRightToBottomLeftOver15(colPlusRow : Int, stone : String) : Boolean {
        var cnt : Int = 0
        for(i in 0..14) {
            if(colPlusRow - 14 + i > 14) break
            if(board[14 - i][colPlusRow - 14 + i] == stone) cnt++
            else cnt = 0
            if(cnt == 5) return true
        }
        return false
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
