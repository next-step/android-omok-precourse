package nextstep.omok

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var omokBoard: Board = Board(15, 15)
    private var currentStoneColor: Int = Board.STONE_BLACK

    fun clickBoard(x: Int, y: Int): StonePlacement? {
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
}