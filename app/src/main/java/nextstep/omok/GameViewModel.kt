package nextstep.omok

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var omokBoard:Board = Board(15, 15)
    private var currentStoneColor:Int = Board.STONE_BLACK

    fun clickBoard(x:Int, y:Int): StonePlacement? {
        val result = omokBoard.tryPlaceStone(x, y, currentStoneColor)
        if(result != null) {
            switchCurrentTurn()
        }

        return result
    }

    private fun switchCurrentTurn() {
        currentStoneColor = if (currentStoneColor == Board.STONE_BLACK) Board.STONE_WHITE else Board.STONE_BLACK
    }

    fun getStonePlacementList():List<StonePlacement> = omokBoard.getStonePlacementList()

    fun getStone(x:Int, y:Int):Int = omokBoard.getStone(x, y)
}