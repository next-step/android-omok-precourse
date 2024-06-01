package nextstep.omok

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

private val NO_STONE = 0
private val BLACK_STONE = 1
private val WHITE_STONE = 2

class CompleteOmokTest {
    private val deltaList = mutableListOf(
        Pair(-1, 0), Pair(1, 0), // 세로
        Pair(0, -1), Pair(0, 1), // 가로
        Pair(-1, -1), Pair(1, 1), // '\' 대각선 방향
        Pair(-1, 1), Pair(1, -1)   // '/' 대각선 방향
    )
    private var pointToPlace = Pair(7, 7)
    private var boardList = MutableList(15) { MutableList(15) { NO_STONE } }

    @Test
    fun testCheckForCompleteOmok() {

        // 빈 보드
        boardList = MutableList(15) { MutableList(15) { NO_STONE } }
        // 오목이 완성되지 않은 경우
        assertFalse(checkForCompleteOmok(BLACK_STONE))
        assertFalse(checkForCompleteOmok(WHITE_STONE))

        // 가로 방향으로 오목(흑돌)이 완성된 경우
        boardList = MutableList(15) { MutableList(15) { NO_STONE } }
        for (i in 4 until 4 + 5)  {
            boardList[7][i] = BLACK_STONE
        }

        assertTrue(checkForCompleteOmok(BLACK_STONE))
        assertFalse(checkForCompleteOmok(WHITE_STONE))

        // 세로 방향으로 오목(백돌)이 완성된 경우
        boardList = MutableList(15) { MutableList(15) { NO_STONE } }
        for (i in 4 until 4 + 5)  {
            boardList[i][7] = WHITE_STONE
        }
        assertFalse(checkForCompleteOmok(BLACK_STONE))
        assertTrue(checkForCompleteOmok(WHITE_STONE))

        // \ 대각선 방향으로 오목(흑돌)이 완성된 경우
        boardList = MutableList(15) { MutableList(15) { NO_STONE } }
        for (i in 5 until 5 + 5) {
                boardList[i][i] = BLACK_STONE
        }
        assertTrue(checkForCompleteOmok(BLACK_STONE))
        assertFalse(checkForCompleteOmok(WHITE_STONE))

        // / 대각선 방향으로 오목(백돌)이 완성된 경우
        boardList = MutableList(15) { MutableList(15) { NO_STONE } }
        for (i in 5 until 5 + 5) {
            boardList[i][14 - i] = WHITE_STONE
        }

        assertFalse(checkForCompleteOmok(BLACK_STONE))
        assertTrue(checkForCompleteOmok(WHITE_STONE))

        // 가로 방향으로 6목(흑돌)이 완성된 경우
        boardList = MutableList(15) { MutableList(15) { NO_STONE } }
        for (i in 4 until 4 + 6)  {
            boardList[7][i] = BLACK_STONE
        }

        assertTrue(checkForCompleteOmok(BLACK_STONE))
        assertFalse(checkForCompleteOmok(WHITE_STONE))

    }

    private fun checkForCompleteOmok(stoneType: Int): Boolean {
        for (i in 0 until 4) {
            if (completeOmok(2 * i, stoneType)) {
                return true
            }
        }
        return false
    }

    private fun completeOmok(deltaIndex: Int, stoneType: Int): Boolean {
        return countStone(deltaList[deltaIndex], stoneType) + countStone(
            deltaList[deltaIndex + 1],
            stoneType
        ) >= 4
    }


    private fun countStone(deltaXY: Pair<Int, Int>, turn: Int): Int {
        var count = 0
        var curX = pointToPlace.first + deltaXY.first
        var curY = pointToPlace.second + deltaXY.second

        while (isInBoard(curX, curY) && isMyStone(curX, curY, turn)) {
            count++
            curX += deltaXY.first
            curY += deltaXY.second
        }
        return count
    }


    private fun isInBoard(curX: Int, curY: Int): Boolean {
        return (curX in 0 until 15 && curY in 0 until 15)
    }

    private fun isMyStone(curX: Int, curY: Int, stoneType: Int): Boolean {
        return boardList[curX][curY] == stoneType
    }
}