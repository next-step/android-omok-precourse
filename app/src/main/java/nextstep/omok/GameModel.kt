package nextstep.omok

// 싱글톤 패턴을 위해 object로 선언
object GameModel {
    var currentPlayer: Player = Player.BLACK // 우선 흑돌로 현재 플레이어 초기화
    var board: Array<Array<Player?>> = Array(15) { arrayOfNulls<Player>(15) } // 15x15 보드, null로 초기화

    fun resetGame() {
        // TODO: 게임 초기화 로직
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = null // 보드의 각 위치를 null로 초기화
            }
        }
    }
    fun switchPlayer() {
        // TODO: 플레이어 전환 로직
    }
    fun checkWinCondition() {
        // TODO: 승리 조건 확인 로직
    }
    fun placeStone() {
        // TODO: 현재 플레이어가 돌을 놓는 로직
    }

    fun handelWin() {
        // TODO : 승리 조건이 만족되었을 때 처리 로직
    }


}