package nextstep.omok

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    var isblackturn : Boolean = true
    lateinit var nowTurnDisplay : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nowTurnDisplay = findViewById<TextView>(R.id.nowTurnDisplay)
        val board = findViewById<TableLayout>(R.id.board)
        board
            .children //모든 자식 요소 가져오기
            .filterIsInstance<TableRow>() //TableRow인 요소만 필터링
            .flatMap { it.children } //각 TableRow의 자식 요소를 평탄화
            .filterIsInstance<ImageView>() //ImageView인 요소만 필터링
            .forEach { view -> view.setOnClickListener { onStonePlace(view) } } // 클릭 시 이미지를 흑/흰돌로 세팅(기존에는 백그라운드만 있다!)
    }

    fun onStonePlace(view: ImageView){
        if(isblackturn) view.setImageResource(R.drawable.black_stone)
        else view.setImageResource(R.drawable.white_stone)
        isblackturn = !isblackturn
        Log.d("testt",isblackturn.toString())
        displayNowTurn(isblackturn)
    }
    fun displayNowTurn(isblackturn : Boolean){
        if(isblackturn){
            nowTurnDisplay.text="현재 차례\nBLACK"
            nowTurnDisplay.setBackgroundColor(Color.BLACK)
            nowTurnDisplay.setTextColor(Color.WHITE)
        }
        else{
            nowTurnDisplay.text="현재 차례\nWHITE"
            nowTurnDisplay.setBackgroundColor(Color.WHITE)
            nowTurnDisplay.setTextColor(Color.BLACK)
        }
    }

}
