package nextstep.omok.util

import nextstep.omok.model.IntersectionState

private const val WIN_CONDITION = 5

object GameStateValidator {
    private var board: List<List<IntersectionState>> = listOf()
    private val directions = arrayOf(Pair(0, 1), Pair(1, 0), Pair(1, 1), Pair(1, -1),)

    fun isWinnerExist(board: List<List<IntersectionState>>): Boolean {
        this.board = board
        for (row in board.indices) {
            for (col in board[row].indices) {
                if (checkBoard(col, row))
                    return true
            }
        }
        return false
    }

    private fun checkBoard(col: Int, row: Int): Boolean {
        val currentIntersection = board[row][col]
        if (currentIntersection == IntersectionState.Empty)
            return false

        val isConditionSatisfied = checkAllDirections(col, row, currentIntersection)
        if (!isConditionSatisfied)
            return false

        return when (currentIntersection) {
            IntersectionState.OnBlackStone -> true
            IntersectionState.OnWhiteStone -> true
            IntersectionState.Empty -> false
        }
    }

    private fun checkAllDirections(
        col: Int, row: Int, currentIntersection: IntersectionState
    ): Boolean {
        for (direction in directions) {
            val (dRow, dCol) = direction
            if (checkSingleDirection(col, row, dCol, dRow, currentIntersection)) {
                return true
            }
        }
        return false
    }

    private fun checkSingleDirection(
        col: Int, row: Int, dCol: Int, dRow: Int, currentIntersection: IntersectionState
    ): Boolean {
        for (i in 0 until WIN_CONDITION) {
            val nx = col + i * dCol
            val ny = row + i * dRow
            if (nx !in board[0].indices || ny !in board.indices || board[ny][nx] != currentIntersection) {
                return false
            }
        }
        return true
    }
}