package nextstep.omok.util

import nextstep.omok.model.IntersectionState
import nextstep.omok.model.Player

private const val WIN_CONDITION = 5

class GameStateValidator {
    private var board: List<List<IntersectionState>> = listOf()
    fun getWinner(board: List<List<IntersectionState>>): Player? {
        this.board = board
        var winner: Player? = null
        for (row in board.indices) {
            for (col in board[row].indices) {
                winner = checkBoard(col, row)
                if (winner != null)
                    return winner
            }
        }

        return winner
    }

    private fun checkBoard(col: Int, row: Int): Player? {
        val currentIntersection = board[row][col]
        if (currentIntersection == IntersectionState.Empty)
            return null


        val isConditionSatisfied = checkAllDirections(col, row, currentIntersection)
        if (!isConditionSatisfied)
            return null

        return when (currentIntersection) {
            IntersectionState.OnBlackStone -> Player.WithBlackStone
            IntersectionState.OnWhiteStone -> Player.WithWhiteStone
            IntersectionState.Empty -> null
        }
    }

    private fun checkAllDirections(
        col: Int, row: Int, currentIntersection: IntersectionState
    ): Boolean {
        val directions = arrayOf(
            Pair(0, 1), Pair(1, 0), Pair(1, 1), Pair(1, -1),
            Pair(-1, 0), Pair(0, -1), Pair(-1, -1), Pair(-1, 1)
        )

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