package nextstep.omok.model

sealed class Player {
    data object WithWhiteStone: Player()
    data object WithBlackStone: Player()
}
