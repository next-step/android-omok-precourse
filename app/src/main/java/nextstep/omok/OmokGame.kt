package nextstep.omok

enum class Stone {
    EMPTY, BLACK, WHITE
}

class OmokGame(private val boardSize: Int) {
    private val board: Array<Array<Stone>> = Array(boardSize) { Array(boardSize) { Stone.EMPTY } }
    private var currentTurn = Stone.BLACK

    fun getStone(x: Int, y: Int): Stone {
        return if (x in 0 until boardSize && y in 0 until boardSize) {
            board[x][y]
        } else {
            Stone.EMPTY
        }
    }
    //돌 놓는 동작 성공 여부
    fun placeStone(x: Int, y: Int): Boolean {
        if (x !in 0 until boardSize || y !in 0 until boardSize || board[x][y] != Stone.EMPTY) {
            return false
        }
        board[x][y] = currentTurn
        val isWin = checkWin(x, y)
        currentTurn = if (currentTurn == Stone.BLACK) Stone.WHITE else Stone.BLACK
        return true
    }
}