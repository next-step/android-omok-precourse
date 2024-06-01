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
var turn = 1
val board = MutableList(15) { MutableList(15) { 0 } }
class MainActivity : AppCompatActivity() {
    lateinit var TextView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val idLists = createImageViewList(this)
        imageViewClick(idLists)



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
        val clickView = idLists[k]
        clickView.setOnClickListener {
            Log.d("qwer", "ImageView clicked: $k")
         createStone(clickView,k)

        }
    }
}

fun createStone(clickView : ImageView,k : Int) {
    if (board[k / 15][k % 15] == 0) {
        if (turn == 1) {
            clickView.setImageResource(R.drawable.white_stone)
            turn = 2
            board[k / 15][k % 15] = 1

        } else {
            clickView.setImageResource(R.drawable.black_stone)
            turn = 1
            board[k / 15][k % 15] = 2
        }
    }else{}
}
fun amIWinner(k: Int, turn:Int) {
    val sequenceStone = MutableList(8) { 0 }
    sequenceStone[0] = sequenceUpCheck(k,turn)
    sequenceStone[1] = sequenceDownCheck(k,turn)
    sequenceStone[2] = sequenceLeftCheck(k,turn)
    sequenceStone[3] = sequenceRightCheck(k,turn)
    sequenceStone[4] = sequenceLeftUpCheck(k,turn)
    sequenceStone[5] = sequenceLeftDownCheck(k,turn)
    sequenceStone[6] = sequenceRightDownCheck(k,turn)
    sequenceStone[7] = sequenceRightUpCheck(k,turn)
}

