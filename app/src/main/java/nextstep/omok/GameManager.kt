package nextstep.omok

import android.util.Log
import android.view.View
import android.widget.ImageView

class GameManager(private val view : MainActivity) {
    val board = Board()
    val BLACK_PLAYER = 1
    val WHITE_PLAYER = -1
    var player = BLACK_PLAYER
    var isGameEnd = false

    fun initGame() {
        board.initBoard()
        player = BLACK_PLAYER
        isGameEnd = false
        view.restartBoardView()
    }
    fun playOneTurn(view : ImageView, row : Int, col : Int){
        if (board.placeStone(row, col, player) && !isGameEnd) {
            this.view.drawStone(view, player)
            player *= -1
        }
    }

    fun playOneTurnWithExceptionHandler(view : ImageView, row : Int, col : Int){
        runCatching {
            playOneTurn(view, row, col)
        }.onFailure {
            Log.d("exceptionCatch", "exceptionOnGameManager")
        }
    }
}