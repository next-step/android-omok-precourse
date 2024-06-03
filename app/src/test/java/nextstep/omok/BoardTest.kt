package nextstep.omok

import org.junit.jupiter.api.DisplayName
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class BoardTest {

    @Test
    @DisplayName("board 위의 한 위치가 비어있는지 확인")
    fun isEmpty() {
        val board: Board = Board()
        assertThat(board.isEmpty(0, 0)).isEqualTo(true)
        board.placeStone(0, 0, 1)
        assertThat(board.isEmpty(0, 0)).isEqualTo(false)
        assertThat(board.isEmpty(16, 1)).isEqualTo(null)
    }

    @Test
    @DisplayName("빈 공간에 돌을 놓을 수 있는지 확인, 못 놓으면 false")
    fun placeStone() {
        val board: Board = Board()
        assertThat(board.placeStone(0, 0, 1)).isEqualTo(true)
        assertThat(board.placeStone(0, 0, -1)).isEqualTo(false)
    }

    @Test
    @DisplayName("정해진 위치를 포함하여 연속으로 5개 이상의 같은 색상의 돌이 있는지 상하좌우, 대각선으로 확인")
    fun isLineOverFive() {
        val board: Board = Board()
        for (i in 0..4) board.placeStone(i, 0, 1)
        for (i in 0..4) assertThat(board.isLineOverFive(i, 0)).isEqualTo(true)
        assertThat(board.isLineOverFive(5, 0)).isEqualTo(false)

    }

    @Test
    @DisplayName("정해진 위치를 포함하여 양 옆으로 5개 이상의 같은 색상의 돌이 있는지 확인")
    fun isVerticalOverFive() {
        val board: Board = Board()
        for (i in 0..4) board.placeStone(0, i, 1)
        for (i in 0..4) assertThat(board.isLineOverFive(0, i)).isEqualTo(true)
        assertThat(board.isLineOverFive(5, 0)).isEqualTo(false)
    }

    @Test
    @DisplayName("정해진 위치를 포함하여 상하로 5개 이상의 같은 색상의 돌이 있는지 확인")
    fun isHorizontalOverFive() {
        val board: Board = Board()
        for (i in 0..4) board.placeStone(i, 0, 1)
        for (i in 0..4) assertThat(board.isLineOverFive(i, 0)).isEqualTo(true)
        assertThat(board.isLineOverFive(5, 0)).isEqualTo(false)
    }

    @Test
    @DisplayName("정해진 위치를 포함하여 대각선으로 5개 이상의 같은 색상의 돌이 있는지 확인")
    fun isDiagonalOverFive() {
        val board: Board = Board()
        for (i in 0..4) board.placeStone(i, i, 1)
        for (i in 0..4) assertThat(board.isLineOverFive(i, i)).isEqualTo(true)
        assertThat(board.isLineOverFive(5, 0)).isEqualTo(false)
    }

    @Test
    @DisplayName("정해진 위치를 포함하여 아래쪽에서 위로 향하는 대각선 모양으로 5개 이상의 같은 색상의 돌이 있는지 확인")
    fun isLowerToUpperDiagonalOverFive() {
        val board: Board = Board()
        for (i in 0..4) board.placeStone(i, 4 - i, 1)
        for (i in 0..4) assertThat(
            board.isLowerToUpperDiagonalOverFive(
                i,
                4 - i,
                1
            )
        ).isEqualTo(true)
    }

    @Test
    @DisplayName("정해진 위치를 포함하여 아래쪽에서 위로 향하는 대각선 모양으로 5개 이상의 같은 색상의 돌이 있는지 확인")
    fun isUpperToLowerDiagonalOverFive() {
        val board: Board = Board()
        for (i in 0..4) board.placeStone(i, i, 1)
        for (i in 0..4) assertThat(board.isUpperToLowerDiagonalOverFive(i, i, 1)).isEqualTo(true)
    }

    @Test
    @DisplayName("정해진 위치를 포함하지 않는 왼쪽 위 대각선에 연속한 같은색상의 돌 갯수 확인")
    fun countUpperLeftDiagonal() {
        val board: Board = Board()
        for (i in 0..4) board.placeStone(i, i, 1)
        for (i in 0..4) assertThat(board.countUpperLeftDiagonal(i, i, 1)).isEqualTo(i)
    }

    @Test
    @DisplayName("정해진 위치를 포함하지 않는 오른쪽 위 대각선에 연속한 같은색상의 돌 갯수 확인")
    fun countUpperRightDiagonal() {
        val board: Board = Board()
        for (i in 0..4) board.placeStone(4 - i, i, 1)
        for (i in 0..4) assertThat(board.countUpperRightDiagonal(4 - i, i, 1)).isEqualTo(4 - i)
    }

    @Test
    @DisplayName("정해진 위치를 포함하지 않는 왼쪽 아래 대각선에 연속한 같은색상의 돌 갯수 확인")
    fun countLowerLeftDiagonal() {
        val board: Board = Board()
        for (i in 0..4) board.placeStone(4 - i, i, 1)
        for (i in 0..4) assertThat(board.countLowerLeftDiagonal(4 - i, i, 1)).isEqualTo(i)
    }

    @Test
    @DisplayName("정해진 위치를 포함하지 않는 오른쪽 아래 대각선에 연속한 같은색상의 돌 갯수 확인")
    fun countLowerRightDiagonal() {
        val board: Board = Board()
        for (i in 0..4) board.placeStone(i, i, 1)
        for (i in 0..4) assertThat(board.countLowerRightDiagonal(i, i, 1)).isEqualTo(4 - i)
    }

    @Test
    @DisplayName("한 위치에 있는 돌의 색이 같은지 확인")
    fun isSameColor() {
        val board: Board = Board()
        board.placeStone(5, 6, -1)
        assertThat(board.isSameColor(5, 6, -1)).isEqualTo(true)
        assertThat(board.isSameColor(5, 6, 1)).isEqualTo(false)
    }
}