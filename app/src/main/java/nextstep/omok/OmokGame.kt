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

    fun checkWin(x: Int, y: Int): Boolean {
        val directions = listOf(
            Pair(1, 0), Pair(0, 1), Pair(1, 1), Pair(1, -1)
        )
        val currentStone = board[x][y]
        for ((dx, dy) in directions) {
            var count = 1
            count += countStonesInDirection(x, y, dx, dy, currentStone)
            count += countStonesInDirection(x, y, -dx, -dy, currentStone)
            if (count >= 5) {
                return true
            }
        }
        return false
    }

    private fun countStonesInDirection(x: Int, y: Int, dx: Int, dy: Int, stone: Stone): Int {
        var count = 0
        var nx = x + dx
        var ny = y + dy
        while (nx in 0 until boardSize && ny in 0 until boardSize && board[nx][ny] == stone) {
            count++
            nx += dx
            ny += dy
        }
        return count
    }
}




