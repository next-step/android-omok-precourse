package nextstep.omok

class GameLogic {
    var currentPlayer = Player.BLACK
        private set
    private var board = Array(15) { Array(15) { Player.NONE } }

    fun placeStone(row: Int, col: Int): GameResult {
        if (board[row][col] != Player.NONE) {
            return GameResult.Occupied
        }

        board[row][col] = currentPlayer
        val isWin = checkWin(row, col)
        val placedPlayer = currentPlayer

        switchPlayer()
        return GameResult.Success(placedPlayer)
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == Player.BLACK) Player.WHITE else Player.BLACK
    }
}

sealed class GameResult {
    data class Success(val player: Player) : GameResult()
    data class Win(val player: Player) : GameResult()
    object Occupied : GameResult()
    object Invalid : GameResult()
}
