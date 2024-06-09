package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BoardActivity : AppCompatActivity() {
    private lateinit var turnImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        turnImage = findViewById(R.id.turnImage)
        updateTurnImage()

        val gameBoardFragment = GameBoardFragment()

        // 게임 초기화
        GameModel.resetGame()


        // 프래그먼트 추가
        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, GameBoardFragment())
                .commit()
        }



    }

    // 턴 이미지 업데이트
    fun updateTurnImage() {
        turnImage.setImageResource(GameModel.getCurrentPlayerStoneResId())
    }
}