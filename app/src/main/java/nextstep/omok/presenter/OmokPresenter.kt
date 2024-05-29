package nextstep.omok.presenter

import nextstep.omok.OmokContract

class OmokPresenter(
    private var activity: OmokContract.OmokView,
    private var model: OmokContract.OmokModel
) : OmokContract.OmokPresenter {
    override fun onIntersectionClick(rowIndex: Int, colIndex: Int) {
        model.updateBoard(rowIndex, colIndex, model.getPlayer().stone)
        activity.placeStone(rowIndex, colIndex, model.getPlayer())
        model.togglePlayer()
    }

    override fun onGameEnd(): Boolean {
        TODO("Not yet implemented")
    }

}