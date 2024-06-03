package nextstep.omok

//class GameManager {
//    private val boardSize = 15
//    private val board: Array<Array<Int>> = Array(boardSize) { Array(boardSize) { 0 } }
//    private var isBlackTurn = true
//    fun getBoard(): Array<Array<Int>> = board
//    fun getCurrentStone(): Int {
//        return if (isBlackTurn) R.drawable.black_stone else R.drawable.white_stone
//    }
//    fun switchTurn() {
//        isBlackTurn = !isBlackTurn
//    }
//}

class GameManager {
    private val BOARD_SIZE = 15
    private val board: Array<Array<Int>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { 0 } }
    private var isBlackTurn = true

    fun getBoard(): Array<Array<Int>> = board

    fun placeStone(row: Int, col: Int): Int {
        if (board[row][col] == 0) {
            board[row][col] = getCurrentStone()
            switchTurn()
            return board[row][col]
        }
        return 0
    }

    private fun getCurrentStone(): Int {
        return if (isBlackTurn) R.drawable.black_stone else R.drawable.white_stone
    }

    private fun switchTurn() {
        isBlackTurn = !isBlackTurn
    }

    fun checkWin(): Boolean {
        return false
    }
}
