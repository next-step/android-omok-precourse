package nextstep.omok.model

sealed class GameState {
    data object OnGoing : GameState()
    data object End : GameState()
}
