package nextstep.omok

class Game(
    paramRows: Int = 15,
    paramCols: Int = 15
) {
    val numRows: Int = paramRows
    val numCols: Int = paramCols
    private var blackTurn = true
    val board = Array(numRows) { arrayOfNulls<String>(numCols) }

    fun isValidPosition(row: Int, col: Int): Boolean? {
        return if (row in 0 until numRows && col in 0 until numCols) {
            board[row][col] == null
        } else {
            null
        }
    }

    fun checkWin(row: Int, col: Int): Boolean{
        val color = board[row][col] ?: return false
        val directions = listOf(
            listOf(0 to 1, 0 to -1),
            listOf(1 to 0, -1 to 0),
            listOf(1 to 1, -1 to -1),
            listOf(1 to -1, -1 to 1)
        )
        return directions.any { direction ->
            val count = 1 + countStoneInDirection(row, col, direction[0], color) + countStoneInDirection(row, col, direction[1], color)
            count >= 5
        }
    }

    fun countStoneInDirection(row: Int, col: Int, direction: Pair<Int, Int>, color: String): Int{
        var count = 0
        var newRow = row + direction.first
        var newCol = col + direction.second
        while (newRow in 0 until numRows && newCol in 0 until numCols && board[newRow][newCol] == color) {
            count++
            newRow += direction.first
            newCol += direction.second
        }
        return count
    }

}