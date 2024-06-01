package nextstep.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameLogicTest {
    private lateinit var gameLogic: GameLogic

    @BeforeEach
    fun setUp() {
        gameLogic = GameLogic()
    }

    @Test
    fun `placeStone should return Success when placing stone on empty cell`() {
        val result = gameLogic.placeStone(0, 0)
        assertThat(result).isInstanceOf(GameResult.Success::class.java)
    }

    @Test
    fun `placeStone should return Occupied when placing stone on occupied cell`() {
        gameLogic.placeStone(0, 0)
        val result = gameLogic.placeStone(0, 0)
        assertThat(result).isInstanceOf(GameResult.Occupied::class.java)
    }

    @Test
    fun `placeStone should return Win when placing stone completes a row of 5`() {
        for (i in 0 until 4) {
            gameLogic.placeStone(0, i)
            gameLogic.switchPlayer()
        }
        val result = gameLogic.placeStone(0, 4)
        assertThat(result).isInstanceOf(GameResult.Win::class.java)
    }

    @Test
    fun `reset should clear the board and set currentPlayer to BLACK`() {
        gameLogic.placeStone(0, 0)
        gameLogic.reset()
        assertThat(gameLogic.currentPlayer).isEqualTo(Player.BLACK)
        for (i in 0 until 15) {
            for (j in 0 until 15) {
                assertThat(gameLogic.getPlayerAt(i, j)).isEqualTo(Player.NONE)
            }
        }
    }
}
