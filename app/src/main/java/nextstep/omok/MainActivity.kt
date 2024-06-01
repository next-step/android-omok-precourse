package nextstep.omok

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val idLists = createImageViewList(this)
        val boardSize = 15
        val emptyBoard = MutableList(boardSize) { MutableList(boardSize) { 0 } }


    }

}
fun createImageViewList(context: Context): List<ImageView> {
    val ImageViewList = mutableListOf<ImageView>()

    for (i in 1..255) {
        val resourceId = context.resources.getIdentifier("button$i", "id", context.packageName)
        val button = (context as? Activity)?.findViewById<ImageView>(resourceId)
        button?.let { ImageViewList.add(it) }
    }
    return ImageViewList
}
