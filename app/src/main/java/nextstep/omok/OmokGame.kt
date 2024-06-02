package nextstep.omok

class OmokGame(private val boardSize: Int) {

    val board = Array(boardSize) { Array(boardSize) { 0 } }
    var currentPlayer = 1 // 1 for black, 2 for white

    fun placeStone(row: Int, col: Int): Boolean {
        if (board[row][col] == 0) {
            board[row][col] = currentPlayer
            return true
        }
        return false
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == 1) 2 else 1
    }

    fun checkWin(row: Int, col: Int, player: Int): Boolean {
        return checkDirection(row, col, player, 0, 1) >= 5 || // Horizontal
                checkDirection(row, col, player, 1, 0) >= 5 || // Vertical
                checkDirection(row, col, player, 1, 1) >= 5 || // Diagonal down-right
                checkDirection(row, col, player, 1, -1) >= 5   // Diagonal up-right
    }

    fun isForbidden(row: Int, col: Int): Boolean {
        if (currentPlayer == 1) {
            return isDoubleThree(row, col) || isDoubleFour(row, col) || isSixInARow(row, col)
        }
        return false
    }

    private fun checkDirection(row: Int, col: Int, player: Int, dRow: Int, dCol: Int): Int {
        var count = 1
        count += countStones(row, col, player, dRow, dCol)
        count += countStones(row, col, player, -dRow, -dCol)
        return count
    }

    private fun countStones(row: Int, col: Int, player: Int, dRow: Int, dCol: Int): Int {
        var count = 0
        var r = row + dRow
        var c = col + dCol
        while (r in 0 until boardSize && c in 0 until boardSize && board[r][c] == player) {
            count++
            r += dRow
            c += dCol
        }
        return count
    }

    fun isDoubleThree(row: Int, col: Int): Boolean {
        return countOpenThrees(row, col, 1, 0) + countOpenThrees(row, col, 0, 1) +
                countOpenThrees(row, col, 1, 1) + countOpenThrees(row, col, 1, -1) > 1
    }

    private fun countOpenThrees(row: Int, col: Int, dRow: Int, dCol: Int): Int {
        var count = 0
        if (isOpenThree(row, col, dRow, dCol)) count++
        if (isOpenThree(row, col, -dRow, -dCol)) count++
        return count
    }

    private fun isOpenThree(row: Int, col: Int, dRow: Int, dCol: Int): Boolean {
        val sequence = mutableListOf<Int>()
        for (i in -4..4) {
            val r = row + i * dRow
            val c = col + i * dCol
            if (r in 0 until boardSize && c in 0 until boardSize) {
                sequence.add(board[r][c])
            } else {
                sequence.add(-1)
            }
        }
        val pattern = listOf(0, 1, 1, 1, 0)
        for (i in 0..sequence.size - pattern.size) {
            if (sequence.subList(i, i + pattern.size) == pattern) {
                return true
            }
        }
        return false
    }

    fun isDoubleFour(row: Int, col: Int): Boolean {
        return countOpenFours(row, col, 1, 0) + countOpenFours(row, col, 0, 1) +
                countOpenFours(row, col, 1, 1) + countOpenFours(row, col, 1, -1) > 1
    }

    private fun countOpenFours(row: Int, col: Int, dRow: Int, dCol: Int): Int {
        var count = 0
        if (isOpenFour(row, col, dRow, dCol)) count++
        if (isOpenFour(row, col, -dRow, -dCol)) count++
        return count
    }

    private fun isOpenFour(row: Int, col: Int, dRow: Int, dCol: Int): Boolean {
        val sequence = mutableListOf<Int>()
        for (i in -4..4) {
            val r = row + i * dRow
            val c = col + i * dCol
            if (r in 0 until boardSize && c in 0 until boardSize) {
                sequence.add(board[r][c])
            } else {
                sequence.add(-1)
            }
        }
        val pattern = listOf(0, 1, 1, 1, 1, 0)
        for (i in 0..sequence.size - pattern.size) {
            if (sequence.subList(i, i + pattern.size) == pattern) {
                return true
            }
        }
        return false
    }

    fun isSixInARow(row: Int, col: Int): Boolean {
        return checkDirection(row, col, currentPlayer, 0, 1) > 5 ||
                checkDirection(row, col, currentPlayer, 1, 0) > 5 ||
                checkDirection(row, col, currentPlayer, 1, 1) > 5 ||
                checkDirection(row, col, currentPlayer, 1, -1) > 5
    }
}
