package nextstep.omok.presenter

import nextstep.omok.OmokContract
import nextstep.omok.model.GameState
import nextstep.omok.model.IntersectionState

class OmokPresenter(
    private var activity: OmokContract.OmokView,
    private var model: OmokContract.OmokModel
) : OmokContract.OmokPresenter {
    override fun onIntersectionClick(rowIndex: Int, colIndex: Int) {
        if (model.getBoardState(rowIndex, colIndex) == IntersectionState.Empty) {
            updateBoard(rowIndex, colIndex)
            checkWinnerExist()
        }
    }

    private fun updateBoard(rowIndex: Int, colIndex: Int) {
        model.updateBoard(rowIndex, colIndex, model.getPlayer().stone)
        activity.placeStone(rowIndex, colIndex, model.getPlayer())
    }

    private fun checkWinnerExist() {
        if (model.getTurn() > 8) {
            model.checkWinnerExist()
            checkCurrentGameState()
        } else {
            onGameOnGoing()
        }
    }

    private fun checkCurrentGameState() {
        when (model.getGameState()) {
            GameState.OnGoing -> {
                onGameOnGoing()
            }
            GameState.End -> {
                onGameEnd()
            }
        }
    }

    private fun onGameOnGoing() {
        model.togglePlayer()
        model.addTurnCount()
        activity.showTurn(model.getTurn(), model.getPlayer())
    }

    private fun onGameEnd() {
        activity.endGame(model.getPlayer())
    }
}