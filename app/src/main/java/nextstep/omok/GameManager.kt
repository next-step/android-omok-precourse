package nextstep.omok

class GameManager {
    private val boardSize = 15
    private val board: Array<Array<Int>> = Array(boardSize) { Array(boardSize) { 0 } }
    private var isBlackTurn = true
    fun getBoard(): Array<Array<Int>> = board
    private fun getCurrentStone(): Int {
        return if (isBlackTurn) R.drawable.black_stone else R.drawable.white_stone
    }

    private fun switchTurn() {
        isBlackTurn = !isBlackTurn
    }
}
