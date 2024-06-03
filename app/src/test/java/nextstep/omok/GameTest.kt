package nextstep.omok

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GameTest {
    private lateinit var game: Game

    @Before
    fun setUp() {
        game = Game(15, 15)
    }

    @Test
    fun testIsValidPosition() {
        assertNull(game.isValidPosition(-1, 0))
        assertNull(game.isValidPosition(0, 15))
        assertTrue(game.isValidPosition(1, 1)!!)
        assertTrue(game.isValidPosition(14, 14)!!)
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
        assertEquals(/* expected = */ 4, /* actual = */ game.countStoneInDirection(0, 0, 1 to 0, "black"))
    }
}