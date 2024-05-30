package nextstep.omok

class OmokController(
	private val view: MainActivity
) {
	private val board = OmokBoard()
	private val curPlayer = Player.BLACK

	init {
		view.updateTurnInfo(curPlayer)
	}

}