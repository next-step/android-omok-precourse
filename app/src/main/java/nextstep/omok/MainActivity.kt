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
        var whosTurn : TextView = findViewById(R.id.user)
        val idLists = createImageViewList(this)
        imageViewClick(idLists,whosTurn)



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

fun createStone(clickView: ImageView, k: Int, whosTurn: TextView) {
    if (board[k / 15][k % 15] == 0) {
        if (turn == 1) {
            clickView.setImageResource(R.drawable.white_stone)
            board[k / 15][k % 15] = 1
            whosTurn.text = "2"
            checkAmIWinner(amIWinner(k,1))
            turn = 2


        } else {
            clickView.setImageResource(R.drawable.black_stone)
            board[k / 15][k % 15] = 2
            whosTurn.text = "1"
            checkAmIWinner(amIWinner(k,2))
            turn = 1
        }
    }else{}
}

fun checkAmIWinner(sequenceStonList : MutableList<Int>){
    if (4 in sequenceStonList){
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

fun sequenceLeftCheck(k: Int, turn: Int): Int {
    var x: Int = k / 15
    var y: Int = k % 15
    var count = 0
    for (i in 1..4) {
        if (y <= 0) {
            break
        } else {
            y -= 1
            count += sequenceCountUp(turn, x, y)
        }
    }
    return count
}
fun sequenceRightCheck(k: Int,turn:Int): Int {
    var x: Int = k / 15
    var y: Int = k % 15
    var count = 0
    for (i in 1..4){
        if (y >= 14) {
            break
        }
        else{
            y += 1
            count += sequenceCountUp(turn,x,y)
        }
    }
    return count
}

fun sequenceUpCheck(k: Int,turn:Int): Int {
    var x: Int = k / 15
    var y: Int = k % 15
    var count = 0
    for (i in 1..4){
        if (x <= 0) {
            break
        }
        else{
            x-=1
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
        if (x >= 14) {
            break
        }
        else{
            x+=1
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
        if (x <= 0 || y<=0) {
            break
        }
        else{
            x -=1
            y -=1
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

        if (x <= 0 || y >= 14) {
            break
        }
        else{
            x-=1
            y+=1
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
        if (x >= 14 || y<=0) {
            break
        }
        else{
            x+=1
            y-=1
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
        if (x >= 14 || y<=14) {
            break
        }
        else{
            x+=1
            y+=1
            count += sequenceCountUp(turn,x,y)
        }
    }
    return count
}
