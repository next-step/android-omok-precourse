package nextstep.omok.model

import nextstep.omok.OmokContract
import nextstep.omok.util.GameStateValidator

class OmokModel: OmokContract.OmokModel {
    private var currentPlayer: Player = Player.WithBlackStone
    private var currentGameState: GameState = GameState.OnGoing
    private val board: MutableList<MutableList<IntersectionState>> = mutableListOf()
    private var turn: Int = 1
    private val gameStateValidator : GameStateValidator by lazy { GameStateValidator() }

    init {
        for(i in 0..14) {
            val oneDimensionList = mutableListOf<IntersectionState>()
            for(j in 0..14) {
                oneDimensionList.add(IntersectionState.Empty)
            }
            board.add(oneDimensionList)
        }
    }

    override fun togglePlayer() {
        currentPlayer = when(currentPlayer) {
            Player.WithBlackStone -> Player.WithWhiteStone
            Player.WithWhiteStone -> Player.WithBlackStone
        }
    }

    override fun updateBoard(rowIndex: Int, colIndex: Int, stone: IntersectionState) {
        board[rowIndex][colIndex] = stone
    }

    override fun getPlayer(): Player {
        return currentPlayer
    }

    override fun updateGameStatus() {
        val winner = gameStateValidator.getWinner(board)
        if (winner == null) {
            return
        } else {
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