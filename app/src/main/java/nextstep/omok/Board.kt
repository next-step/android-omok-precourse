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

}