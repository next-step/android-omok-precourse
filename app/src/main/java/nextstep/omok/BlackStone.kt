package nextstep.omok

class BlackStone : Stone {
    override val resId: Int = R.drawable.black_stone
    override fun toString(): String {
        return "흑돌"
    }
    val highlightedResId = R.drawable.black_stone_highlight
}