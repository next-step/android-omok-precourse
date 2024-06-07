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
        /* currentPlayer 관련 함수 */
        fun togglePlayer()
        fun getPlayer(): PlayerType

        /* omokBoard 관련 함수 */
        fun updateBoard(rowIndex: Int, colIndex: Int, stone: IntersectionState)

        /* turn 관련 함수 */
        fun addTurnCount()
        fun getTurn(): Int

        /* currentGameState 관련 함수 */
        fun updateGameStatus()
        fun getGameState(): GameState
    }

    interface OmokPresenter {
        fun onIntersectionClick(rowIndex: Int, colIndex: Int)
        // Player에 맞는 돌을 놓는다.
        // turn이 9이상일 때부터, 게임이 종료되었는지 판단한다.
    }
}