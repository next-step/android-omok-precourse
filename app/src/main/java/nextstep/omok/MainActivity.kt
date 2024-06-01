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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import org.w3c.dom.Text
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var TextView : TextView
    var turn = 1
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
fun imageViewClick(idLists : List<ImageView>){
     for (k in idLists.indices){
         afterClick(k, idLists)
     }

     }

fun afterClick(k : Int, idLists : List<ImageView>) {
    if (k < 255) {
        val imageView = idLists[k]
        imageView.setOnClickListener {

        }
    }
}

