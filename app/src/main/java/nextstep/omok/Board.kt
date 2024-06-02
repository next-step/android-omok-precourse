package nextstep.omok

class Board(private val size: Int) {
    private val board: Array<Array<Int>> = Array(size) { Array(size) { 0 } }

    // 해당 위치에 돌을 놓았는지 확인
    // 비어있으면 플레이어의 번호로 변경
    fun checkPlaceStone(x: Int, y: Int, player: Int): Boolean{
        if (board[x][y] == 0) {
            board[x][y] = player
            return true
        }
        return false
    }

    // 판의 크기를 반환
    fun boardSize(): Int {
        return size
    }

    // 특정 위치의 돌 색깔을 확인
    fun checkStoneColor(x: Int, y: Int): Int {
        return board[x][y]
    }

    // 오목판 초기화
    fun resetGame() {
        for (i in 0 until size) {
            for (j in 0 until size) {
                board[i][j] = 0
            }
        }
    }
}