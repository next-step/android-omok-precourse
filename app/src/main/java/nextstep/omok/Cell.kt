package nextstep.omok

import android.content.Context
import android.util.AttributeSet

class Cell (
    context: Context,
    attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatImageView(context, attrs){
    var position: Pair<Int, Int>? = null
    var currentStone: Stone? = null // black or white or null

    fun isEmpty(): Boolean {
        val transparentDrawable = resources.getDrawable(android.R.color.transparent, null)
        return drawable == null || drawable.constantState == transparentDrawable.constantState
    }

    fun placeStone(stone: Stone) {
        setImageResource(stone.resId)
        currentStone = stone
    }
}