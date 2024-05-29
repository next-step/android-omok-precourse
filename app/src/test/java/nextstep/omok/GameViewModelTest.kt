package nextstep.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class GameViewModelTest {
    private val gameViewModel: GameViewModel = GameViewModel()

    @Test
    @DisplayName("redundant click on same point should return null result")
    fun testGameViewModel_ClickBoard_ThrowsNullOnRedundantClick(){
        gameViewModel.clickBoard(1, 1)
        assertThat(gameViewModel.clickBoard(1, 1)).isNull()
    }
    @Test
    @DisplayName("following serial board clicks should return valid results for each")
    fun testGameViewModel_ClickBoard_ReturnValidResult(){
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
}