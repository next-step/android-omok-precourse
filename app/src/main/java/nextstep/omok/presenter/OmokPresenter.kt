package nextstep.omok.presenter

import nextstep.omok.OmokContract
import nextstep.omok.model.GameState

class OmokPresenter(
    private var activity: OmokContract.OmokView,
    private var model: OmokContract.OmokModel
) : OmokContract.OmokPresenter {
    override fun onIntersectionClick(rowIndex: Int, colIndex: Int) {
        model.updateBoard(rowIndex, colIndex, model.getPlayer().stone)
        activity.placeStone(rowIndex, colIndex, model.getPlayer())

        if (model.getTurn() > 8) {
            model.updateGameStatus()
            checkGameState()
        } else {
            model.togglePlayer()
            model.addTurnCount()
        }
        activity.showTurn(model.getTurn(), model.getPlayer())
    }

    override fun onGameEnd() {
        activity.endGame(model.getPlayer())
    }

    private fun checkGameState() {
        when (model.getGameState()) {
            GameState.OnGoing -> {
                model.togglePlayer()
                model.addTurnCount()
            }
            GameState.End -> {
                onGameEnd()
            }
        }
    }
}