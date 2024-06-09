package nextstep.omok.model

import nextstep.omok.R

sealed class PlayerType {
    abstract val stone: IntersectionState
    abstract val resourceId: Int
    data object WithWhiteStone: PlayerType() {
        override val stone = IntersectionState.OnWhiteStone
        override val resourceId: Int = R.drawable.white_stone
    }

    data object WithBlackStone: PlayerType() {
        override val stone = IntersectionState.OnBlackStone
        override val resourceId: Int = R.drawable.black_stone
    }
}
