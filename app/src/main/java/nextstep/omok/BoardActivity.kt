package nextstep.omok

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
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

    // 승리 다이얼로그 표시
    fun showWinDialog() {
        val dialogView = layoutInflater.inflate(R.layout.popup_victory , null)
        // 다이얼로그 생성 및 표시
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this).setView(dialogView).create()
        dialog.show()

        setWinnerImage(dialog)

        dialog.findViewById<Button>(R.id.newGameButton)?.setOnClickListener {
            resetGame()
            dialog.dismiss()
        }
        dialog.findViewById<Button>(R.id.mainMenuButton)?.setOnClickListener {
            // 메인 메뉴로 이동
            navigateToMainMenu()
        }
    }

    // 승자 이미지 설정
    private fun setWinnerImage(dialog: androidx.appcompat.app.AlertDialog) {
        val winnerImageView = dialog.findViewById<ImageView>(R.id.winner)
        GameModel.winner?.let {
            winnerImageView?.setImageResource(it.stoneResId)
        }
    }
    // 메인 메뉴로 이동
    private fun navigateToMainMenu() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}