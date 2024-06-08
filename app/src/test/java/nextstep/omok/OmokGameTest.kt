package nextstep.omok

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName

class OmokGameTest {

    private val boardSize = 15

    @Test
    @DisplayName("빈 위치에 돌을 놓을 수 있는지 테스트")
    fun testPlaceStone() {
        val game = OmokGame(boardSize)
        assertTrue(game.placeStone(0, 0)) // 빈 곳에 돌을 놓을 수 있어야 함
        assertFalse(game.placeStone(0, 0)) // 이미 돌이 있는 곳에 다시 놓을 수 없어야 함
    }

    @Test
    @DisplayName("정해진 위치에 돌이 놓였는지 테스트")
    fun testGetStone() {
        val game = OmokGame(boardSize)
        game.placeStone(0, 0)
        assertEquals(Stone.BLACK, game.getStone(0, 0))
        game.placeStone(1, 1)
        assertEquals(Stone.WHITE, game.getStone(1, 1))
    }

    @Test
    @DisplayName("빈 위치 확인 테스트")
    fun testIsEmpty() {
        val game = OmokGame(boardSize)
        assertTrue(game.getStone(0, 0) == Stone.EMPTY)
        game.placeStone(0, 0)
        assertFalse(game.getStone(0, 0) == Stone.EMPTY)
    }
    @Test
    @DisplayName("현재 턴 변경 테스트")
    fun testSwitchTurn() {
        val game = OmokGame(boardSize)
        game.placeStone(0, 0)
        assertEquals(Stone.BLACK, game.getStone(0, 0))
        game.placeStone(1, 1)
        assertEquals(Stone.WHITE, game.getStone(1, 1))
    }
    @Test
    @DisplayName("오목판 초기화 테스트")
    fun testInitializeBoard() {
        val game = OmokGame(boardSize)
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                assertEquals(Stone.EMPTY, game.getStone(i, j))
            }
        }
    }
}
