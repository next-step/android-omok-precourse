package nextstep.omok.model

import nextstep.omok.OmokContract
import nextstep.omok.util.GameStateValidator

class OmokModel : OmokContract.OmokModel {
    private var currentPlayer: CurrentPlayer = CurrentPlayer
    private val omokBoard = OmokBoard
    private var turn: Int = 1
    private var currentGameState: GameState = GameState.OnGoing
    private val gameStateValidator: GameStateValidator by lazy { GameStateValidator }

    /* currentPlayer 관련 함수 */
    override fun togglePlayer() {
        currentPlayer.togglePlayer()
    }

    override fun getPlayer(): PlayerType {
        return currentPlayer.getPlayer()
    }


    /* omokBoard 관련 함수 */
    override fun updateBoard(rowIndex: Int, colIndex: Int, stone: IntersectionState) {
        omokBoard.updateBoard(rowIndex, colIndex, stone)
    }


    /* turn 관련 함수 */
    override fun addTurnCount() {
        turn++
    }

    override fun getTurn(): Int {
        return turn
    }


    /* currentGameState 관련 함수 */
    override fun checkWinnerExist() {
        if (gameStateValidator.isWinnerExist(omokBoard.getBoard())) {
            endGame()
        }
    }

    override fun getGameState(): GameState {
        return currentGameState
    }

    private fun endGame() {
        currentGameState = GameState.End
    }
}