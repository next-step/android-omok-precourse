package nextstep.omok

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
var turn = 1
val board = MutableList(15) { MutableList(15) { 0 } }
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}
fun createImageViewList(context: Context): List<ImageView> {
    val ImageViewList = mutableListOf<ImageView>()

    for (i in 1..255) {
        val resourceId = context.resources.getIdentifier("button$i", "id", context.packageName)
        val image = (context as? Activity)?.findViewById<ImageView>(resourceId)
        image?.let { ImageViewList.add(it) }
    }
    return ImageViewList
}

fun imageViewClick(idLists: List<ImageView>, whosTurn: TextView){
    for (k in idLists.indices){
        afterClick(k, idLists,whosTurn)
    }

}
fun afterClick(k: Int, idLists: List<ImageView>, whosTurn: TextView) {
    if (k < 255) {
        val clickView = idLists[k]
        clickView.setOnClickListener {
            createStone(clickView,k,whosTurn)

        }
    }
}

