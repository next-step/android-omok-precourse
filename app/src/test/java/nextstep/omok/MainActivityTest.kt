package nextstep.omok

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class MainActivityTest {
    var turn: User = User.BLACK
    private var boards = Array(BOARD_SIZE) { Array<BoardState>(BOARD_SIZE) { BoardState.EMPTY } }

    fun isWin(idx: Int): Boolean {
        val col = idx / BOARD_SIZE
        val row = idx % BOARD_SIZE

        return checkDirection(col, row, 1, 0) ||
                checkDirection(col, row, 0, 1) ||
                checkDirection(col, row, 1, 1) ||
                checkDirection(col, row, 1, -1)
    }

    fun checkDirection(col: Int, row: Int, colStep: Int, rowStep: Int): Boolean {
        val target = boards[col][row]
        var count = 1

        count += countDirection(col, row, colStep, rowStep, target)
        count += countDirection(col, row, -colStep, -rowStep, target)

        return count >= 5
    }

    fun countDirection(
        col: Int,
        row: Int,
        colStep: Int,
        rowStep: Int,
        target: BoardState
    ): Int {
        var count = 0
        var currentCol = col + colStep
        var currentRow = row + rowStep

        while (true) {
            if (currentCol < 0 || currentCol >= BOARD_SIZE) break
            if (currentRow < 0 || currentRow >= BOARD_SIZE) break
            if (boards[currentCol][currentRow] != target) break
            count++
            currentCol += colStep
            currentRow += rowStep
        }

        return count
    }

    fun placeStone(idx: Int) {
        val col = idx / BOARD_SIZE
        val row = idx % BOARD_SIZE

        boards[col][row] = if (turn == User.BLACK) BoardState.BLACK else BoardState.WHITE

    }

    fun changeTurn() {
        this.turn = if (turn == User.BLACK) User.WHITE else User.BLACK
    }

    // 해당 인덱스가 빈 공간인지 확인한다.
    fun isEmptySpace(idx: Int): Boolean {
        return boards[idx / BOARD_SIZE][idx % BOARD_SIZE] == BoardState.EMPTY
    }

    @Test
    fun `차례는 흑돌 먼저 시작이며 번갈아가며 진행된다`(){
        assertEquals(turn, User.BLACK)
        changeTurn()
        assertEquals(turn, User.WHITE)
        changeTurn()
        assertEquals(turn, User.BLACK)
    }

    @Test
    fun `유효한 칸에만 돌을 둘 수 있다`(){
        placeStone(0)
        assertEquals(isEmptySpace(0),false)
        assertEquals(isEmptySpace(8),true)
    }

    @Test
    fun `가로,세로, 대각선으로 연속된 같은 돌이 5개 존재한다면 승리한다`(){
        placeStone(0)
        placeStone(1)
        placeStone(2)
        placeStone(3)
        placeStone(4)

        assertEquals(isWin(4),true)
    }
}