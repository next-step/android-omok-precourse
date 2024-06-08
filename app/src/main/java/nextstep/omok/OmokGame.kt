package nextstep.omok

const val PLAYER_BLACK = 'B'
const val PLAYER_WHITE = 'W'
const val BOARD_SIZE = 15

class OmokGame {
    var currentPlayer = PLAYER_BLACK

    private val board = Array(BOARD_SIZE) { Array<Char?>(BOARD_SIZE) { null } }

    fun placeStone(row: Int, col: Int): Boolean {
        if (board[row][col] == null) {
            board[row][col] = currentPlayer
            return true
        }
        return false
    }

    fun checkWin(row: Int, col: Int): Boolean {
        val directions = listOf(
            listOf(0 to 1, 0 to -1), listOf(1 to 0, -1 to 0), listOf(1 to 1, -1 to -1), listOf(1 to -1, -1 to 1)
        )
        for (direction in directions) {
            var count = 1
            for ((dr, dc) in direction) {
                count += countStonesInDirection(row, col, dr, dc)
            }
            if (count >= 5) {
                return true
            }
        }
        return false
    }

    private fun countStonesInDirection(row: Int, col: Int, dr: Int, dc: Int): Int {
        var count = 0
        var r = row + dr
        var c = col + dc

        while (r in 0 until BOARD_SIZE && c in 0 until BOARD_SIZE && board[r][c] == currentPlayer) {
            count++
            r += dr
            c += dc
        }
        return count
    }

    fun togglePlayer() {
        currentPlayer = if (currentPlayer == PLAYER_BLACK) PLAYER_WHITE else PLAYER_BLACK
    }

    fun resetGame() {
        for (row in 0 until BOARD_SIZE) {
            for (col in 0 until BOARD_SIZE) {
                board[row][col] = null
            }
        }
        currentPlayer = PLAYER_BLACK
    }

    fun getStone(row: Int, col: Int): Char? {
        return board[row][col]
    }

    fun isBoardFull(): Boolean {
        return board.all { row -> row.all { cell -> cell != null }}
    }
}
