package nextstep.omok

import nextstep.omok.model.GameState
import nextstep.omok.model.IntersectionState
import nextstep.omok.model.PlayerType

interface OmokContract {
    interface OmokView {
        fun showTurn(currentTurn: Int, currentPlayerType: PlayerType)

        fun placeStone(rowIndex: Int, colIndex: Int, playerType: PlayerType)

        fun endGame(winner: PlayerType)
    }

    interface OmokModel {
        fun togglePlayer()
        fun updateBoard(rowIndex: Int, colIndex: Int, stone: IntersectionState)
        fun getPlayer(): PlayerType
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