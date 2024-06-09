package nextstep.omok.model

object CurrentPlayer {
    private var currentPlayer: PlayerType = PlayerType.WithBlackStone

    fun togglePlayer() {
        currentPlayer = when (currentPlayer) {
            PlayerType.WithBlackStone -> PlayerType.WithWhiteStone
            PlayerType.WithWhiteStone -> PlayerType.WithBlackStone
        }
    }

    fun getPlayer(): PlayerType {
        return currentPlayer
    }
}