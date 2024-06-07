package nextstep.omok

import nextstep.omok.model.GameState
import nextstep.omok.model.IntersectionState
import nextstep.omok.model.Player

interface OmokContract {
    interface OmokView {
        fun showTurn(currentTurn: Int)

        fun placeStone(rowIndex: Int, colIndex: Int, playerType: Player)

        fun showWinner(winner: Player)
    }

    interface OmokModel {
        fun togglePlayer()
        fun updateBoard(rowIndex: Int, colIndex: Int, stone: IntersectionState)
        fun getPlayer(): Player
        fun updateGameStatus()
        fun addTurnCount()
        fun getTurn(): Int
        fun getGameState(): GameState
    }

    interface OmokPresenter {
        fun onIntersectionClick(rowIndex: Int, colIndex: Int)
        // Player에 맞는 돌을 놓는다.
        // turn이 9이상일 때부터, 게임이 종료되었는지 판단한다.

        fun onGameEnd()
        // 승자를 표시한다.
        // 더 이상 게임을 진행할 수 없다.
    }
}