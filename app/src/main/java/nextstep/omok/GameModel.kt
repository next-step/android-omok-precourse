package nextstep.omok

import android.util.Log

// 싱글톤 패턴을 위해 object로 선언
object GameModel {
    var currentPlayer: Player = Player.BLACK // 우선 흑돌로 현재 플레이어 초기화
    var board: Array<Array<Player?>> = Array(15) { arrayOfNulls<Player>(15) } // 15x15 보드, null로 초기화

    // 방향 체크할 때 쓰기 위한 변수
    private val directions = mapOf(
        "Horizontal" to Pair(1, 0),  // 가로
        "Vertical" to Pair(0, 1),    // 세로
        "PositiveDiagonal" to Pair(1, 1),   // 대각선 \
        "NegativeDiagonal" to Pair(1, -1)   // 대각선 /
    )

    fun resetGame() {
        // NOTE: 게임 초기화 로직
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = null // 보드의 각 위치를 null로 초기화
            }
        }
        currentPlayer = Player.BLACK // 흑돌로 초기화
    }
    fun switchPlayer() {
        // NOTE: 플레이어 전환 로직
        currentPlayer = if (currentPlayer == Player.BLACK) Player.WHITE else Player.BLACK
    }
    fun checkWinCondition(x: Int, y: Int):Boolean{
        // NOTE: 승리 조건 확인 로직
        return checkDirection(x, y, directions["Horizontal"]!!) ||  // 가로
                checkDirection(x, y, directions["Vertical"]!!) ||  // 세로
                checkDirection(x, y, directions["PositiveDiagonal"]!!) ||  // 대각선 \
                checkDirection(x, y, directions["NegativeDiagonal"]!!)    // 대각선 /
    }

    // 각 방향 넣으면 해당 방향으로 5개 이상의 돌이 있는지 확인
    private fun checkDirection(x: Int, y: Int, direction: Pair<Int, Int>): Boolean {
        val (dx, dy) = direction
        val count = countStones(x, y, dx, dy) + countStones(x, y, -dx, -dy) + 1
        return count >= 5
    }

    // 해당 방향으로 돌이 몇개 있는지 확인
    private fun countStones(x: Int, y: Int, dx: Int, dy: Int): Int {
        var count = 0
        var nx = x + dx
        var ny = y + dy

        while (nx in board.indices && ny in board[nx].indices && board[nx][ny] == currentPlayer) {
            count++
            nx += dx
            ny += dy
        }

        return count
    }



    fun placeStone(x: Int, y: Int): Boolean {
        // TODO: 현재 플레이어가 돌을 놓는 로직
        if (board[x][y] == null) {
            board[x][y] = currentPlayer // 현재 플레이어가 돌을 놓음
            return true
        }
        return false
    }

    fun handelWin() {
        // TODO : 승리 조건이 만족되었을 때 처리 로직
        return
    }

    fun getCurrentPlayerStoneResId(): Int {
        // 현재 플레이어의 돌 리소스 파일 ID를 반환
        return currentPlayer.stoneResId
    }


}