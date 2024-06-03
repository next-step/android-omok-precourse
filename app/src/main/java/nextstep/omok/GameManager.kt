package nextstep.omok

import android.util.Log

class GameManager {
    private val BOARD_SIZE = 15
    private val board: Array<Array<Int>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { 0 } }
    private var isBlackTurn = true
    val blackStone = R.drawable.black_stone
    fun getSize(): Int = BOARD_SIZE

    fun placeStone(row: Int, col: Int): Int {
        if (board[row][col] == 0) {
            board[row][col] = getCurrentStone()
            switchTurn()
            Log.d("stone", "ID: "+board[row][col])
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

    fun getStoneName(stone: Int): String {
        if (stone==blackStone) return "흑돌"
        else return "백돌"
    }
    fun checkWin(id: Int, row: Int, col: Int): Boolean {
        if(isVLinked(id, row, col)||isHLinked(id, row, col)||isRLDiagLinked(id, row, col)||isLRDiagLinked(id,row,col)) return true
        else return false
    }

    private fun isVLinked(id: Int, row: Int, col: Int): Boolean {
        var count: Int = 1
        for (i in row - 1 downTo 0) { // 위
            if (board[i][col] == id) {
                count++
            } else {
                break
            }
        }
        for (i in row + 1 until board.size) { // 아래
            if (board[i][col] == id) {
                count++
            } else {
                break
            }
        }
        return count >= 5 // 5개 이상의 돌이 연결되어 있으면 true
    }

    private fun isHLinked(id: Int, row: Int, col: Int): Boolean { // 좌우 체크
        var count: Int = 1
        for (i in col - 1 downTo 0) { // 오른쪽
            if (board[row][i] == id) {
                count++
            } else {
                break
            }
        }
        for (i in col + 1 until board.size) { // 오른쪽
            if (board[row][i] == id) {
                count++
            } else {
                break
            }
        }
        return count >= 5 // 5개 이상의 돌이 연결되어 있으면 true
    }

    private fun isLRDiagLinked(id: Int, row: Int, col: Int): Boolean {
        var count = 1

        var i = row - 1 // 왼쪽 위 대각선
        var j = col - 1
        while (i >= 0 && j >= 0 && board[i][j] == id) {
            count++
            i--
            j--
        }

        i = row + 1 // 오른쪽 아래 대각선
        j = col + 1
        while (i < board.size && j < board.size && board[i][j] == id) {
            count++
            i++
            j++
        }
        return count >= 5
    }

    private fun isRLDiagLinked(id: Int, row: Int, col: Int): Boolean {
        var count = 1

        var i = row - 1 // 왼쪽 위 대각선
        var j = col + 1
        while (i >= 0 && j < board.size && board[i][j] == id) {
            count++
            i--
            j++
        }

        i = row + 1 // 오른쪽 아래 대각선
        j = col - 1
        while (i < board.size && j >= 0 && board[i][j] == id) {
            count++
            i++
            j--
        }
        return count >= 5
    }

    fun resetGame() {
        board.forEach { row -> row.fill(0) }
        isBlackTurn = true
    }
}
