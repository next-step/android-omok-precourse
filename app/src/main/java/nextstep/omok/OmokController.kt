package nextstep.omok

class OmokController(
	private val view: MainActivity
) {
	private val board = OmokBoard()
	private var curPlayer = Player.BLACK
	private var isGameOver = false

	init {
		view.updateTurnInfo(curPlayer)
	}

	fun tryPutStone(r: Int, c: Int) {
		if (isGameOver) return

		if (board.isCellAvailable(r, c)) {
			// 보드 정보 업데이트
			board.putStone(r, c, curPlayer)
			view.updateBoardCell(r, c, curPlayer)

			if (board.checkCurStoneIsWinner(r, c)) {
				// 승리자 발생
				view.showWinnerInfo(curPlayer)
				isGameOver = true
			}
			else if (board.isDraw()) {
				// 무승부
				view.showWinnerInfo(Player.NONE)
				isGameOver = true
			}
			else {
				// 플레이어 변경
				curPlayer = if (curPlayer == Player.BLACK) Player.WHITE else Player.BLACK
				view.updateTurnInfo(curPlayer)
			}
		}
	}
}