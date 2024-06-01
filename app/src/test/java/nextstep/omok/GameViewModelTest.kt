package nextstep.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class GameViewModelTest {
    private val gameViewModel: GameViewModel = GameViewModel()

    @Test
    @DisplayName("redundant click on same point should return null result")
    fun testGameViewModel_ClickBoard_ThrowsNullOnRedundantClick() {
        gameViewModel.clickBoard(1, 1)
        assertThat(gameViewModel.clickBoard(1, 1)).isNull()
    }

    @Test
    @DisplayName("following serial board clicks should return valid results for each")
    fun testGameViewModel_ClickBoard_ReturnValidResult() {
        assertThat(gameViewModel.clickBoard(1, 1)).isEqualTo(
            StonePlacement(1, 1, Board.STONE_EMPTY, Board.STONE_BLACK)
        )
        assertThat(gameViewModel.clickBoard(1, 2)).isEqualTo(
            StonePlacement(1, 2, Board.STONE_EMPTY, Board.STONE_WHITE)
        )
        assertThat(gameViewModel.clickBoard(1, 3)).isEqualTo(
            StonePlacement(1, 3, Board.STONE_EMPTY, Board.STONE_BLACK)
        )
        assertThat(gameViewModel.clickBoard(1, 4)).isEqualTo(
            StonePlacement(1, 4, Board.STONE_EMPTY, Board.STONE_WHITE)
        )
    }

    @Test
    @DisplayName("invalid inputs should be ignored and should not change state")
    fun testGameViewModel_ClickBoard_IgnoreInvalidInput() {
        assertThat(gameViewModel.clickBoard(1, 1)).isEqualTo(
            StonePlacement(1, 1, Board.STONE_EMPTY, Board.STONE_BLACK)
        )
        assertThat(gameViewModel.clickBoard(1, 2)).isEqualTo(
            StonePlacement(1, 2, Board.STONE_EMPTY, Board.STONE_WHITE)
        )
        assertThat(gameViewModel.clickBoard(1, 2)).isNull()
        assertThat(gameViewModel.clickBoard(1, -1)).isNull()
        assertThat(gameViewModel.getStone(1, 2)).isEqualTo(Board.STONE_WHITE)
        assertThat(gameViewModel.clickBoard(1, 3)).isEqualTo(
            StonePlacement(1, 3, Board.STONE_EMPTY, Board.STONE_BLACK)
        )
    }

    @Test
    @DisplayName("After following placements, any other point but specified points should not be portion of Omok")
    fun testGameViewModel_CheckOmok_Completeness() {
        gameViewModel.clickBoard(2, 2)
        gameViewModel.clickBoard(10, 10)
        gameViewModel.clickBoard(3, 3)
        gameViewModel.clickBoard(8, 10)
        gameViewModel.clickBoard(4, 4)
        gameViewModel.clickBoard(6, 10)
        gameViewModel.clickBoard(5, 5)
        gameViewModel.clickBoard(4, 10)
        gameViewModel.clickBoard(6, 6)
        gameViewModel.clickBoard(2, 10)

        val points = listOf(Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6))
        for (i in 0 until 15) {
            for (j in 0 until 15) {
                assertThat(gameViewModel.checkOmok(i, j)).isEqualTo(points.contains(Pair(i, j)))
            }
        }
    }

    @Test
    @DisplayName("After following placements, specified points should be portion of Omok")
    fun testGameViewModel_CheckOmok_Sixmok() {
        gameViewModel.clickBoard(12, 10)
        gameViewModel.clickBoard(0, 0)
        gameViewModel.clickBoard(10, 10)
        gameViewModel.clickBoard(1, 0)
        gameViewModel.clickBoard(8, 10)
        gameViewModel.clickBoard(2, 0)
        gameViewModel.clickBoard(6, 10)
        gameViewModel.clickBoard(4, 0)
        gameViewModel.clickBoard(4, 10)
        gameViewModel.clickBoard(5, 0)
        gameViewModel.clickBoard(2, 10)
        gameViewModel.clickBoard(3, 0)

        assertThat(gameViewModel.checkOmok(0, 0)).isTrue()
        assertThat(gameViewModel.checkOmok(1, 0)).isTrue()
        assertThat(gameViewModel.checkOmok(2, 0)).isTrue()
        assertThat(gameViewModel.checkOmok(3, 0)).isTrue()
        assertThat(gameViewModel.checkOmok(4, 0)).isTrue()
        assertThat(gameViewModel.checkOmok(5, 0)).isTrue()
    }

    @Test
    @DisplayName("All stones in board should be removed after reset() called")
    fun testGameViewModel_ResetClearsBoard() {
        gameViewModel.clickBoard(10, 10)
        gameViewModel.clickBoard(8, 6)
        gameViewModel.clickBoard(3, 4)

        gameViewModel.reset()

        assertThat(gameViewModel.getStone(10, 10)).isEqualTo(Board.STONE_EMPTY)
        assertThat(gameViewModel.getStone(8, 6)).isEqualTo(Board.STONE_EMPTY)
        assertThat(gameViewModel.getStone(3, 4)).isEqualTo(Board.STONE_EMPTY)
    }

    @Test
    @DisplayName("Inactive")
    fun testGameViewModel_InactiveBoard() {
        gameViewModel.gameActive = false

        gameViewModel.clickBoard(1, 1)
        gameViewModel.clickBoard(2, 2)
        gameViewModel.clickBoard(3, 3)

        gameViewModel.gameActive = true

        gameViewModel.clickBoard(6, 6)
        gameViewModel.clickBoard(5, 5)
        gameViewModel.clickBoard(4, 4)

        assertThat(gameViewModel.getStone(1, 1)).isEqualTo(Board.STONE_EMPTY)
        assertThat(gameViewModel.getStone(2, 2)).isEqualTo(Board.STONE_EMPTY)
        assertThat(gameViewModel.getStone(3, 3)).isEqualTo(Board.STONE_EMPTY)

        assertThat(gameViewModel.getStone(6, 6)).isNotEqualTo(Board.STONE_EMPTY)
        assertThat(gameViewModel.getStone(5, 5)).isNotEqualTo(Board.STONE_EMPTY)
        assertThat(gameViewModel.getStone(4, 4)).isNotEqualTo(Board.STONE_EMPTY)
    }
}