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

    // 연속된 돌의 개수를 계산
    fun countStone(x: Int, y: Int, player: Int, dx: Int, dy: Int): Int {
        var count = 0
        var nx = x
        var ny = y

        while (nx in 0 until board.boardSize() && ny in 0 until board.boardSize()
            && board.checkStoneColor(nx, ny) == player
        ) {
            count++
            nx += dx
            ny += dy
        }
        return count

    }

    // 승자가 생겼는지 확인
    fun checkWinner(x: Int, y: Int, player: Int): Boolean {
        val directs = listOf(
            listOf(1, 0), listOf(0, 1), listOf(-1, 0), listOf(0, -1),
            listOf(1, 1), listOf(1, -1), listOf(-1, 1), listOf(-1, -1)
        )
        directs.forEach { (dx, dy) ->
            if (countStone(x,y,player,dx,dy) >= 5) {
                return true
            }
        }
        return false
    }
}