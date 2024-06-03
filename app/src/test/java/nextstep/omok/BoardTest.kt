package nextstep.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                assertThat(board.checkIfPointIsEmpty(j, i)).isTrue()
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
        assertThat(board.checkIfPointIsEmpty(x, y)).isFalse()
    }

    @ParameterizedTest(name = "following serial operation ({2}) on {0} x {1} size board should not return any null")
    @MethodSource("generateBoardClickTestValidInputs")
    fun testBoardTryPlaceStone_validInputs(rows: Int, cols: Int, inputs: List<Pair<Int, Int>>) {
        val board = Board(rows, cols)
        for (input in inputs) {
            assertThat(
                board.tryPlaceStone(
                    input.first,
                    input.second,
                    Board.STONE_WHITE
                )
            ).isNotNull()
        }
    }

    @ParameterizedTest(name = "following serial operation ({2}) on {0} x {1} size board should have returned some nulls")
    @MethodSource("generateInvalidBoardClickTestInputs")
    fun testBoardTryPlaceStone_invalidInputs(rows: Int, cols: Int, inputs: List<Pair<Int, Int>>) {
        val board = Board(rows, cols)
        val list = mutableListOf<Any?>()
        for (input in inputs) {
            list.add(board.tryPlaceStone(input.first, input.second, Board.STONE_WHITE))
        }
        assertThat(list).containsNull()
    }

    @ParameterizedTest(name = "board should return correct length of longest line")
    @MethodSource("generateBoardGetLengthTestInputs")
    fun testBoardGetLongestLineLength(inputs: List<Pair<Int, Int>>, checkPoints: List<Pair<Int, Int>>, expectedResults: List<Int>){
        val board = Board(15, 15)
        for (input in inputs) {
            board.tryPlaceStone(input.first, input.second, Board.STONE_WHITE)
        }
        for (point in checkPoints.withIndex()){
            assertThat(board.getLongestLineLength(point.value.first, point.value.second))
                .isEqualTo(expectedResults[point.index])
        }
    }

    private fun generateBoardClickTestValidInputs(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(
                15, 15,
                listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3), Pair(0, 4))
            ),
            Arguments.of(
                15, 15,
                listOf(Pair(10, 11), Pair(0, 0), Pair(14, 14), Pair(0, 14), Pair(14, 0))
            ),
            Arguments.of(
                5, 4,
                getBoardClickTestPath(5, 4)
            ),
            Arguments.of(
                15, 20,
                getBoardClickTestPath(15, 20)
            )
        )
    }

    private fun generateInvalidBoardClickTestInputs(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(
                15, 15,
                listOf(Pair(0, 0), Pair(0, 0))
            ),
            Arguments.of(
                15, 15,
                listOf(Pair(0, -1))
            ),
            Arguments.of(
                15, 15,
                listOf(Pair(1, 1), Pair(2, 2), Pair(15, 15))
            )
        )
    }

    private fun generateBoardGetLengthTestInputs(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(
                listOf(Pair(1, 0), Pair(2, 0), Pair(3, 0), Pair(4, 0)),
                listOf(Pair(2, 0)),
                listOf(4)
            ),
            Arguments.of(
                listOf(Pair(2, 2), Pair(3, 3), Pair(4, 4), Pair(5, 5), Pair(6, 6)),
                listOf(Pair(5, 6), Pair(3, 3)),
                listOf(0, 5)
            ),
            Arguments.of(
                listOf(
                    Pair(10, 14), Pair(11, 13), Pair(12, 12), Pair(13, 11), Pair(14, 10),
                    Pair(11, 12), Pair(11, 11)
                ),
                listOf(Pair(11, 13), Pair(11, 12)),
                listOf(5, 3)
            )
        )
    }

    private fun getBoardClickTestPath(rows: Int, cols: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result.add(Pair(j, i))
            }
        }
        return result
    }
}