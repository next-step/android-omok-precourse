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

        if (isWin) {
            return GameResult.Win(placedPlayer)
        }

        switchPlayer()
        return GameResult.Success(placedPlayer)
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == Player.BLACK) Player.WHITE else Player.BLACK
    }

    fun getPlayerAt(row: Int, col: Int): Player {
        return board[row][col]
    }

    private fun checkWin(row: Int, col: Int): Boolean {
        val directions = listOf(
            listOf(-1 to 0, 1 to 0),  // Vertical
            listOf(0 to -1, 0 to 1),  // Horizontal
            listOf(-1 to -1, 1 to 1), // Diagonal \
            listOf(-1 to 1, 1 to -1)  // Diagonal /
        )
        return directions.any { direction ->
            countStones(row, col, direction) >= 5
        }
    }

    private fun countStones(row: Int, col: Int, direction: List<Pair<Int, Int>>): Int {
        return direction.sumOf { (dx, dy) ->
            generateSequence(1) { it + 1 }
                .map { row + it * dx to col + it * dy }
                .takeWhile { (r, c) -> r in 0 until 15 && c in 0 until 15 && board[r][c] == currentPlayer }
                .count()
        } + 1
    }
}

sealed class GameResult {
    data class Success(val player: Player) : GameResult()
    data class Win(val player: Player) : GameResult()
    object Occupied : GameResult()
    object Invalid : GameResult()
}
