package nextstep.omok

class GameManager(private val board: Board) {
    private var playerTurn = 1

    // 플레이어의 턴 변경
    fun switchTurn() {
        playerTurn = if (playerTurn == 1) 2 else 1
    }
    // 현재 어떤 플레이어의 턴인지 확인
    fun checkTurn(): Int {
        return playerTurn
    }
}