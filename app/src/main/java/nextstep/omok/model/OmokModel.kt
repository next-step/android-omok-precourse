package nextstep.omok.model

import nextstep.omok.OmokContract
import nextstep.omok.util.GameStateValidator

class OmokModel : OmokContract.OmokModel {
    private var currentPlayer: CurrentPlayer = CurrentPlayer
    private var currentGameState: GameState = GameState.OnGoing
    private val omokBoard = OmokBoard
    private var turn: Int = 1
    private val gameStateValidator: GameStateValidator by lazy { GameStateValidator() }
    private var winner: PlayerType? = null

    override fun togglePlayer() {
        currentPlayer.togglePlayer()
    }

    override fun getPlayer(): PlayerType {
        return currentPlayer.getPlayer()
    }

    override fun updateBoard(rowIndex: Int, colIndex: Int, stone: IntersectionState) {
        omokBoard.updateBoard(rowIndex, colIndex, stone)
    }

    override fun updateGameStatus() {
        val winner = gameStateValidator.getWinner(omokBoard.getBoard())
        if (winner == null) {
            return
        } else {
            this.winner = winner
            endGame()
        }
    }

    override fun addTurnCount() {
        turn++
    }

    override fun getTurn(): Int {
        return turn
    }


    override fun getGameState(): GameState {
        return currentGameState
    }

    private fun endGame() {
        currentGameState = GameState.End
    }
}