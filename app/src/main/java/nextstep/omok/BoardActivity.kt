package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BoardActivity : AppCompatActivity() {
    private lateinit var turnImage: ImageView
    private lateinit var newGame: TextView
    private lateinit var gameBoardFragment: GameBoardFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        turnImage = findViewById(R.id.turnImage)
        newGame = findViewById(R.id.newGameButton)

        // 프래그먼트 추가
        if (savedInstanceState==null){
            gameBoardFragment = GameBoardFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, gameBoardFragment)
                .commit()
        } else {
        gameBoardFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as GameBoardFragment
    }

        GameModel.resetGame()
        updateTurnImage()

        newGame.setOnClickListener {
            resetGame()
        }

    }

    // 턴 이미지 업데이트
    fun updateTurnImage() {
        turnImage.setImageResource(GameModel.getCurrentPlayerStoneResId())
    }

    // 새 게임 버튼 클릭시 게임 초기화
    fun resetGame() {
        GameModel.resetGame()
        gameBoardFragment.resetBoard()
        updateTurnImage()
    }
}