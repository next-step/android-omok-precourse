package nextstep.omok

class OmokController(private val view: MainActivity) {
	private val board = OmokBoard()
	private var curPlayer = Player.BLACK
	private var isGameOver = false

	init {
		view.updateTurnInfo(curPlayer)
	}

	fun tryPutStone(r: Int, c: Int) {
		if (!board.isCellAvailable(r, c) || isGameOver) return

		// 보드 정보 업데이트
		board.putStone(r, c, curPlayer)
		view.updateBoardCell(r, c, curPlayer)

		if (board.checkCurStoneIsWinner(r, c)) {
			view.showWinnerInfo(curPlayer)
			isGameOver = true
		}
		else if (board.isDraw()) {
			view.showWinnerInfo(Player.NONE)
			isGameOver = true
		}
		else {	// 승부가 나지 않음 -> 플레이어 변경
			curPlayer = if (curPlayer == Player.BLACK) Player.WHITE else Player.BLACK
			view.updateTurnInfo(curPlayer)
		}
	}
}