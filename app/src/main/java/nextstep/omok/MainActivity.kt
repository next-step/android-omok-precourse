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
        TextView.findViewById<TextView>(R.id.user)
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
            checkAmIWinner(amIWinner(k,turn))

        } else {
            clickView.setImageResource(R.drawable.black_stone)
            turn = 1
            board[k / 15][k % 15] = 2
            checkAmIWinner(amIWinner(k,turn))
        }
    }else{}
}

fun checkAmIWinner(sequenceStonList : MutableList<Int>){
    if (4 in sequenceStonList){
        Log.d("qwer", "checkAmIWinner: WINN!!!!")
    }else{}
}

fun amIWinner(k: Int, turn:Int) : MutableList<Int>{
    val sequenceStone = MutableList(8) { 0 }
    sequenceStone[0] = sequenceUpCheck(k,turn)
    sequenceStone[1] = sequenceDownCheck(k,turn)
    sequenceStone[2] = sequenceLeftCheck(k,turn)
    sequenceStone[3] = sequenceRightCheck(k,turn)
    sequenceStone[4] = sequenceLeftUpCheck(k,turn)
    sequenceStone[5] = sequenceLeftDownCheck(k,turn)
    sequenceStone[6] = sequenceRightDownCheck(k,turn)
    sequenceStone[7] = sequenceRightUpCheck(k,turn)
    return sequenceStone
}
fun sequenceCountUp(turn:Int,x:Int,y:Int): Int {
    if (turn == board[x][y]) {
        return 1
    }else{
        return 0
    }
}

fun sequenceUpCheck(k: Int,turn:Int): Int {
    var x: Int = k / 15
    var y: Int = k % 15
    var count = 0
    for (i in 1..4){
        if (y == 0) {
            break
        }
        else{
            var y = y-1
            count += sequenceCountUp(turn,x,y)
        }
    }
    return count
}
fun sequenceDownCheck(k: Int,turn:Int): Int {
    var x: Int = k / 15
    var y: Int = k % 15
    var count = 0
    for (i in 1..4){
        if (y == 14) {
            break
        }
        else{
            var y = y+1
            count += sequenceCountUp(turn,x,y)
        }
    }
    return count
}

fun sequenceLeftCheck(k: Int,turn:Int): Int {
    var x: Int = k / 15
    var y: Int = k % 15
    var count = 0
    for (i in 1..4){
        if (x == 0) {
            break
        }
        else{
            var x = x-1
            count += sequenceCountUp(turn,x,y)
        }
    }
    return count
}

fun sequenceRightCheck(k: Int,turn:Int): Int {
    var x: Int = k / 15
    var y: Int = k % 15
    var count = 0
    for (i in 1..4){
        if (x == 14) {
            break
        }
        else{
            var x = x+1
            count += sequenceCountUp(turn,x,y)
        }
    }
    return count
}

fun sequenceLeftUpCheck(k: Int,turn:Int): Int {
    var x: Int = k / 15
    var y: Int = k % 15
    var count = 0
    for (i in 1..4){
        if (x == 0 || y==0) {
            break
        }
        else{
            var x = x-1
            var y = y-1
            count += sequenceCountUp(turn,x,y)
        }
    }
    return count
}

fun sequenceLeftDownCheck(k: Int,turn:Int): Int {
    var x: Int = k / 15
    var y: Int = k % 15
    var count = 0
    for (i in 1..4){
        if (x == 0 || y==14) {
            break
        }
        else{
            var x = x-1
            var y = y+1
            count += sequenceCountUp(turn,x,y)
        }
    }
    return count
}
fun sequenceRightUpCheck(k: Int,turn:Int): Int {
    var x: Int = k / 15
    var y: Int = k % 15
    var count = 0
    for (i in 1..4){
        if (x == 14 || y==0) {
            break
        }
        else{
            var x = x+1
            var y = y-1
            count += sequenceCountUp(turn,x,y)
        }
    }
    return count
}
fun sequenceRightDownCheck(k: Int,turn:Int): Int {
    var x: Int = k / 15
    var y: Int = k % 15
    var count = 0
    for (i in 1..4){
        if (x == 14 || y==14) {
            break
        }
        else{
            var x = x+1
            var y = y+1
            count += sequenceCountUp(turn,x,y)
        }
    }
    return count
}
