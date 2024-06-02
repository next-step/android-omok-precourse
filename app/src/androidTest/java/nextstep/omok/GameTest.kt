package nextstep.omok

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameTest {
    private lateinit var game: Game
    @Before
    fun setUp(){
        game = Game(15, 15, true)
    }

    @Test
    fun testIsValidPosition() {
        assertNull(game.isValidPosition(-1, 0))  // 범위를 벗어난 경우
        assertNull(game.isValidPosition(0, 15))  // 범위를 벗어난 경우
        assertTrue(game.isValidPosition(0, 0)!!)   // 유효한 경우
        assertTrue(game.isValidPosition(14, 14)!!) // 유효한 경우
    }

    @Test
    fun testCheckWin() {
        for (i in 0..4) {
            game.board[0][i] = "black"
        }
        assertTrue(game.checkWin(0, 4))
    }

    @Test
    fun testCountStonesInDirection() {
        for (i in 0..4) {
            game.board[i][0] = "black"
        }
        assertEquals(4, game.countStonesInDirection(0, 0, 1 to 0, "black"))
    }
}