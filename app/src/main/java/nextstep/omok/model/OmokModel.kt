package nextstep.omok.model

import nextstep.omok.OmokContract

class OmokModel: OmokContract.OmokModel {
    private var currentPlayer: Player = Player.WithBlackStone
    private val board: MutableList<MutableList<IntersectionState>> = mutableListOf()
    private var turn: Int = 0

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

    override fun updateGameStatus(gameStatus: GameState) {
        TODO("Not yet implemented")
    }

    override fun addTurnCount() {
        TODO("Not yet implemented")
    }

}