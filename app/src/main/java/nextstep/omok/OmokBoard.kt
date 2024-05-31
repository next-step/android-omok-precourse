package nextstep.omok

class OmokBoard(private val size: Int = 15) {
	private val board: Array<Array<Player>> = Array(size) { Array(size) { Player.NONE } }

	fun isCellAvailable(r: Int, c: Int): Boolean = (board[r][c] == Player.NONE)

	fun putStone(r: Int, c: Int, player: Player) {
		board[r][c] = player
	}





}