package nextstep.omok

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokGameTest {

    private lateinit var game: OmokGame

    @BeforeEach
    fun setUp() {
        game = OmokGame()
    }

    @Test
    fun testPlaceStone() {
        assertTrue(game.placeStone(0, 0))
        assertEquals(PLAYER_BLACK, game.getStone(0, 0))
        assertFalse(game.placeStone(0, 0))
    }

    @Test
    fun testTogglePlayer() {
        game.togglePlayer()
        assertEquals(PLAYER_WHITE, game.currentPlayer)
        game.togglePlayer()
        assertEquals(PLAYER_BLACK, game.currentPlayer)
    }

    @Test
    fun testCheckWin() {
        for (i in 0 until 5) {
            game.placeStone(0, i)
        }
        assertTrue(game.checkWin(0, 4))
        assertFalse(game.checkWin(1, 2))
    }

    @Test
    fun testResetGame() {
        game.placeStone(0, 0)
        game.resetGame()
        assertNull(game.getStone(0, 0))
        assertEquals(PLAYER_BLACK, game.currentPlayer)
    }
}