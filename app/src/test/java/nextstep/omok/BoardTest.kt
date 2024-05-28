package nextstep.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BoardTest {
    @ParameterizedTest(name = "you can place a stone in every {0} x {1} spaces of board after initialized.")
    @CsvSource(
        "1, 1",
        "5, 10",
        "15, 15",
        "40, 20",
        "100, 100"
    )
    fun testBoardCheckPlaceable_allValidSpaces(rows: Int, cols: Int) {
        val board = Board(rows, cols)
        for(i in 0 until rows) {
            for(j in 0 until cols) {
                assertThat(board.checkStonePlaceable(j,i)).isTrue()
            }
        }
    }

    @ParameterizedTest(name = "you cannot place at ({3}, {2}) on {0} x {1} size board.")
    @CsvSource(
        "25, 15, 15, 7",
        "15, 20, 7, 15",
        "15, 15, -1, 0",
        "20, 20, 25, -1",
        "30, 30, 10, -10"
    )
    fun testBoardCheckPlaceable_invalidSpaces(rows: Int, cols: Int, x: Int, y: Int) {
        val board = Board(rows, cols)
        assertThat(board.checkStonePlaceable(x,y)).isFalse()
    }
}