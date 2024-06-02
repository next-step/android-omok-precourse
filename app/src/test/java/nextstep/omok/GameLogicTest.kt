package nextstep.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameLogicTest {

    private val boardSize = 15
    private lateinit var game: OmokGame

    @BeforeEach
    fun setUp() {
        game = OmokGame(boardSize)
    }

    @Test
    fun `horizontal win check`() {
        for (i in 0 until 5) {
            game.board[0][i] = 1
        }
        val result = game.checkWin(0, 2, 1)
        assertThat(result).isTrue()
    }

    @Test
    fun `vertical win check`() {
        for (i in 0 until 5) {
            game.board[i][0] = 1
        }
        val result = game.checkWin(2, 0, 1)
        assertThat(result).isTrue()
    }

    @Test
    fun `diagonal down-right win check`() {
        for (i in 0 until 5) {
            game.board[i][i] = 1
        }
        val result = game.checkWin(2, 2, 1)
        assertThat(result).isTrue()
    }

    @Test
    fun `diagonal up-right win check`() {
        for (i in 0 until 5) {
            game.board[4 - i][i] = 1
        }
        val result = game.checkWin(2, 2, 1)
        assertThat(result).isTrue()
    }

    @Test
    fun `double three forbidden move`() {
        // Set up a double three situation
        game.board[1][2] = 1
        game.board[1][3] = 1
        game.board[1][5] = 1
        game.board[1][6] = 1
        game.board[2][3] = 1
        game.board[3][3] = 1
        game.board[4][3] = 1

        val result = game.isDoubleThree(1, 4)
        assertThat(result).isTrue()
    }

    @Test
    fun `double four forbidden move`() {
        // Set up a double four situation
        game.board[1][2] = 1
        game.board[1][3] = 1
        game.board[1][4] = 1
        game.board[1][6] = 1
        game.board[1][7] = 1
        game.board[1][8] = 1
        game.board[0][5] = 1
        game.board[2][5] = 1

        val result = game.isDoubleFour(1, 5)
        assertThat(result).isTrue()
    }

    @Test
    fun `six in a row forbidden move`() {
        // Set up a six in a row situation
        for (i in 0 until 6) {
            game.board[0][i] = 1
        }
        val result = game.isSixInARow(0, 2)
        assertThat(result).isTrue()
    }
}
