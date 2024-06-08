package nextstep.omok

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        // 게임 초기화
        GameModel.resetGame()

        // 프래그먼트 추가
        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, GameBoardFragment())
                .commit()
        }



    }
}