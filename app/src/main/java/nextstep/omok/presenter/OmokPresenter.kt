package nextstep.omok.presenter

import nextstep.omok.OmokContract
import nextstep.omok.model.IntersectionState
import nextstep.omok.model.Player

class OmokPresenter(
    private var activity: OmokContract.OmokView,
    private var model: OmokContract.OmokModel
) : OmokContract.OmokPresenter {
    override fun onIntersectionClick(rowIndex: Int, colIndex: Int) {
        val stone = if (model.getPlayer() == Player.WithWhiteStone) {
            IntersectionState.OnWhiteStone
        } else {
            IntersectionState.OnBlackStone
        }

        model.updateBoard(rowIndex, colIndex, stone)
        activity.placeStone(
            rowIndex = rowIndex,
            colIndex = colIndex,
            playerType = model.getPlayer()
        )
        model.togglePlayer()
    }

    override fun onGameEnd(): Boolean {
        TODO("Not yet implemented")
    }

}