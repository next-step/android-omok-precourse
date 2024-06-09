package nextstep.omok

import Referee
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RefereeTest {

    private val boardSize = 15
    private lateinit var boardState: Array<Int?>
    private lateinit var referee: Referee

    @BeforeEach
    fun `설정`() {
        boardState = Array(boardSize * boardSize) { null }
        referee = Referee(boardSize, boardState)
    }

    @Test
    fun `세로 승리 테스트`() {
        for (i in 0..4) {
            boardState[i * boardSize] = 0
        }
        assertTrue(referee.checkState(0))
    }

    @Test
    fun `가로 승리 테스트`() {
        for (i in 0..4) {
            boardState[i] = 1
        }
        assertTrue(referee.checkState(0))
    }

    @Test
    fun `대각선 승리 테스트_왼to오`() {
        for (i in 0..4) {
            boardState[i * (boardSize + 1)] = 0
        }
        assertTrue(referee.checkState(0))
    }

    @Test
    fun `대각선 승리 테스트_오to왼`() {
        for (i in 0..4) {
            boardState[(i + 1) * (boardSize - 1)] = 1
        }
        assertTrue(referee.checkState(boardSize - 1))
    }

    @Test
    fun `승리 없음 테스트`() {
        boardState[0] = 0
        boardState[1] = 1
        boardState[boardSize] = 1
        boardState[boardSize + 1] = 0
        assertFalse(referee.checkState(0))
        assertFalse(referee.checkState(1))
        assertFalse(referee.checkState(boardSize))
        assertFalse(referee.checkState(boardSize + 1))
    }
}