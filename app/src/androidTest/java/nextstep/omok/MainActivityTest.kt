package nextstep.omok

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainActivityTest {
    val BLACK_STONE: Int = 0
    val WHITE_STONE: Int = 1
    val NO_STONE: Int = -1
    val INITIAL_STONE_COUNT: Int = 1

    var turn: Int = 0
    val boardSize: Int = 15
    var boardState = Array(boardSize){ IntArray(boardSize) {NO_STONE} }
    val directions: Array<Array<Int>> = arrayOf(
        arrayOf(0, 1),
        arrayOf(1, 0),
        arrayOf(1, 1),
        arrayOf(1, -1)
    )

    @Test
    fun testWinHorizontal() {
        // 가로 방향으로 승리 조건을 만족하는 경우
        for (i in 0 until 5) {
            boardState[7][i] = BLACK_STONE
        }
        assertTrue(checkWin(7, 2, BLACK_STONE))
    }

    @Test
    fun testWinVertical() {
        // 세로 방향으로 승리 조건을 만족하는 경우
        for (i in 0 until 5) {
            boardState[i][7] = BLACK_STONE
        }
        assertTrue(checkWin(2, 7, BLACK_STONE))
    }

    @Test
    fun testWinDiagonal() {
        // 대각선 방향으로 승리 조건을 만족하는 경우
        for (i in 0 until 5) {
            boardState[i][i] = BLACK_STONE
        }
        assertTrue(checkWin(2, 2, BLACK_STONE))
    }

    @Test
    fun testNoWin() {
        // 승리 조건을 만족하지 않는 경우
        boardState[7][0] = BLACK_STONE
        boardState[7][2] = BLACK_STONE
        boardState[7][3] = BLACK_STONE
        boardState[7][4] = BLACK_STONE
        assertFalse(checkWin(7, 2, BLACK_STONE))
    }

    fun checkWin(row: Int, column: Int, stoneColor: Int): Boolean {
        for ((dx, dy) in directions) {
            var count = INITIAL_STONE_COUNT

            count += countStonesInDirection(row, column, dx, dy, stoneColor)
            count += countStonesInDirection(row, column, -dx, -dy, stoneColor)

//            Log.d("testt","방향 ("+dx+","+dy+")"+" 연속되는 돌 개수:"+count)

            if (count >= 5) return true
        }
        return false
    }

    fun countStonesInDirection(row: Int, col: Int, dx: Int, dy: Int, stoneColor: Int): Int {
        var count = 0
        var x = row + dx
        var y = col + dy
        while (x in 0..(boardSize-1) && y in 0..(boardSize-1)
            && boardState[x][y] == stoneColor) {
            count++

            x += dx
            y += dy
        }
        return count
    }
}
