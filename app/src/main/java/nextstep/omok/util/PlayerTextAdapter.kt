package nextstep.omok.util

import nextstep.omok.model.PlayerType

const val PLAYER_WITH_BLACK_STONE = "흑돌"
const val PLAYER_WITH_WHITE_STONE = "백돌"

fun adaptPlayerText(currentPlayerType: PlayerType): String {
    return when (currentPlayerType) {
        PlayerType.WithBlackStone -> PLAYER_WITH_BLACK_STONE
        PlayerType.WithWhiteStone -> PLAYER_WITH_WHITE_STONE
    }
}