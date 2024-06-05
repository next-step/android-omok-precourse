package nextstep.omok

class WhiteStone : Stone {
    override val resId: Int = R.drawable.white_stone

    override fun toString(): String {
        return "백돌"
    }

    val highlightedResId = R.drawable.white_stone_highlight
}
