package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children //모든 자식 요소 가져오기
            .filterIsInstance<TableRow>() //TableRow인 요소만 필터링
            .flatMap { it.children } //각 TableRow의 자식 요소를 평탄화
            .filterIsInstance<ImageView>() //ImageView인 요소만 필터링
            .forEach { view -> view.setOnClickListener { onStonePlace(view) } } // 클릭 시 이미지를 흑돌로 세팅(기존에는 백그라운드만 있다!)
    }

    fun onStonePlace(view: ImageView){
        view.setImageResource(R.drawable.white_stone)
    }

}
