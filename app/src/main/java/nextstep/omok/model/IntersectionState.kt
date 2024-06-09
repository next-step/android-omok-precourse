package nextstep.omok.model

sealed class IntersectionState {
    data object Empty: IntersectionState()
    data object OnWhiteStone: IntersectionState()
    data object OnBlackStone: IntersectionState()
}
