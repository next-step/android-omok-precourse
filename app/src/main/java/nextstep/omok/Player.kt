package nextstep.omok

enum class Player(val color: String, val stoneResId: Int) {
    BLACK("black", R.drawable.black_stone),
    WHITE("white", R.drawable.white_stone)
}