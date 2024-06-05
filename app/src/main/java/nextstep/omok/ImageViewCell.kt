package nextstep.omok

import android.content.Context
import android.util.AttributeSet

class ImageViewCell (
    context: Context,
    attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatImageView(context, attrs), Cell{
    override var position: Pair<Int, Int>? = null
    var currentStone: Stone? = null // black or white or null

    override fun isEmpty(): Boolean {
        val transparentDrawable = resources.getDrawable(android.R.color.transparent, null)
        return drawable == null || drawable.constantState == transparentDrawable.constantState
    }

    fun placeStone(stone: Stone) {
        setImageResource(stone.resId)
        currentStone = stone
    }
}