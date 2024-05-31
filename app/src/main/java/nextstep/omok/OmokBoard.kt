package nextstep.omok

class OmokBoard(private val size: Int = 15) {
	private val board: Array<Array<Player>> = Array(size) { Array(size) { Player.NONE } }

	fun isCellAvailable(r: Int, c: Int): Boolean = (board[r][c] == Player.NONE)

	fun putStone(r: Int, c: Int, player: Player) {
		board[r][c] = player
	}

	fun checkCurStoneIsWinner(r: Int, c: Int): Boolean {
		if (board[r][c] == Player.NONE) {
			return false
		}

		if (checkVertical(r, c, board[r][c])) {
			return true
		}
		if (checkHorizontal(r, c, board[r][c])) {
			return true
		}
		if (checkDiagonal(r, c, board[r][c])) {
			return true
		}
		return false
	}

	private fun isInBoardRange(r: Int, c: Int): Boolean
		= (r in 0 until size) && (c in 0 until size)

	private fun countContinueCell(r: Int, c: Int, player: Player, direction: Direction): Int {
		var continueCount = 0
		for (delta in 1..size) {
			val nxtR = r + direction.deltaR * delta
			val nxtC = c + direction.deltaC * delta

			if (!isInBoardRange(nxtR, nxtC)) break
			if (board[nxtR][nxtC] != player) break

			++continueCount
		}
		return continueCount
	}

	private fun checkVertical(r: Int, c: Int, player: Player): Boolean {
		val upCount = countContinueCell(r, c, player, Direction.UP)
		val downCount = countContinueCell(r, c, player, Direction.DOWN)
		return (upCount + downCount + 1) >= 5
	}

	private fun checkHorizontal(r: Int, c: Int, player: Player): Boolean {
		val leftCount = countContinueCell(r, c, player, Direction.LEFT)
		val rightCount = countContinueCell(r, c, player, Direction.RIGHT)
		return (leftCount + rightCount + 1) >= 5
	}

	private fun checkDiagonal(r: Int, c: Int, player: Player): Boolean {
		val upLeftCount = countContinueCell(r, c, player, Direction.UP_LEFT)
		val downRightCount = countContinueCell(r, c, player, Direction.DOWN_RIGHT)
		if ((upLeftCount + downRightCount + 1) >= 5) {
			return true
		}

		val upRightCount = countContinueCell(r, c, player, Direction.UP_RIGHT)
		val downLeftCount = countContinueCell(r, c, player, Direction.DOWN_LEFT)
		if ((upRightCount + downLeftCount + 1) >= 5) {
			return true
		}

		return false
	}


}