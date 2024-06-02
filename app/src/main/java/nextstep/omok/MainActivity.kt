package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    // 객체 필드 변수
    private lateinit var omokBoard: Board
    private lateinit var gameManager: GameManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var size = 15
        omokBoard = Board(15)
        gameManager = GameManager(omokBoard)

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
                            if (omokBoard.checkPlaceStone(rowIdx, colIdx, gameManager.checkTurn())) {
                                view.setImageResource(
                                    if (gameManager.checkTurn() == 1) R.drawable.black_stone
                                    else R.drawable.white_stone
                                )

                                if (gameManager.checkWinner(rowIdx, colIdx, gameManager.checkTurn())) {
                                    // 게임 종료
                                    Toast.makeText(this, "플레이어${gameManager.checkTurn()} 승리", Toast.LENGTH_LONG).show()
                                } else {
                                    gameManager.switchTurn()
                                }
                            }
                        }
                    }

            }

    }
}
