package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    // 객체 필드 변수
    private lateinit var omokBoard: Board
    private lateinit var gameManager: GameManager
    private lateinit var gameResult: LinearLayout
    private lateinit var winnerStone: ImageView
    private var isGameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var size = 15
        omokBoard = Board(15)
        gameManager = GameManager(omokBoard)
        gameResult = findViewById(R.id.game_result)
        winnerStone = findViewById(R.id.stone_color)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIdx, rowBoard ->
                rowBoard
                    .children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIdx, view ->
                        view.setOnClickListener {
                            if (isGameOver) return@setOnClickListener

                            if (omokBoard.checkPlaceStone(rowIdx, colIdx, gameManager.checkTurn())) {
                                view.setImageResource(
                                    if (gameManager.checkTurn() == 1) R.drawable.black_stone
                                    else R.drawable.white_stone
                                )

                                if (gameManager.checkWinner(rowIdx, colIdx, gameManager.checkTurn())) {
                                    // 게임 종료
                                    showGameResult(gameManager.checkTurn())
                                    isGameOver = true
                                } else {
                                    gameManager.switchTurn()
                                }
                            }
                        }
                    }

            }

    }

    private fun showGameResult(winnerPlayer: Int) {
        gameResult.visibility = LinearLayout.VISIBLE
        winnerStone.setImageResource(
            if (winnerPlayer == 1) R.drawable.black_stone
            else R.drawable.white_stone
        )
    }
}
