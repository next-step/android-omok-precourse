package nextstep.omok

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var omokBoard: Board = Board(BOARD_HEIGHT, BOARD_WIDTH)
    private var currentStoneColor: Int = Board.STONE_BLACK
    var gameActive: Boolean = true

    fun clickBoard(x: Int, y: Int): StonePlacement? {
        if(!gameActive)
            return null

        val result = omokBoard.tryPlaceStone(x, y, currentStoneColor)
        if (result != null) {
            switchCurrentTurn()
        }

        return result
    }

    private fun switchCurrentTurn() {
        currentStoneColor =
            if (currentStoneColor == Board.STONE_BLACK) Board.STONE_WHITE else Board.STONE_BLACK
    }

    fun getStonePlacementList(): List<StonePlacement> = omokBoard.getStonePlacementStack()

    fun getStone(x: Int, y: Int): Int = omokBoard.getStone(x, y)

    fun checkOmok(x: Int, y: Int): Boolean {
        val stoneType = omokBoard.getStone(x, y)
        if (stoneType != Board.STONE_BLACK && stoneType != Board.STONE_WHITE) {
            return false
        }
        val length = omokBoard.getLongestLineLength(x, y)
        return length >= 5
    }

    fun reset(){
        gameActive = true
        omokBoard = Board(BOARD_HEIGHT, BOARD_WIDTH)
        currentStoneColor = Board.STONE_BLACK
    }

    companion object{
        const val BOARD_WIDTH = 15
        const val BOARD_HEIGHT = 15
    }
}