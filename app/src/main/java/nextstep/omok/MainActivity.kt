package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    var isBlackTurn = true  //플레이어1(흑돌) or 플레이어2(백돌) 여부
    val boardState = Array(15) { IntArray(15) {0} }  //보드 상태(2차원 배열), 0: 빈 칸, 1: 흑돌, 2: 백돌
    var gameEnded = false  //게임 종료 여부

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)  //보드
        val restartButton = findViewById<Button>(R.id.restartButton)  //Restart Button

        /*
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setOnClickListener { view.setImageResource(R.drawable.black_stone) } }*/
    }
}
