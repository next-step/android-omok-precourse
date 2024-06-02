package nextstep.omok

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

const val BOARD_SIZE: Int = 15

class MainActivity : AppCompatActivity() {
    private var turn: User = User.BLACK
    private var boards = Array(BOARD_SIZE) { Array<BoardState>(BOARD_SIZE) { BoardState.EMPTY } }
    private fun changeTurn() {
        this.turn = if (turn == User.BLACK) User.WHITE else User.BLACK
    }

    // 해당 인덱스가 빈 공간인지 확인한다.
    private fun isEmptySpace(idx: Int): Boolean {
        return boards[idx / BOARD_SIZE][idx % BOARD_SIZE] == BoardState.EMPTY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)

        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { idx, view ->
                view.setOnClickListener {
                    if (isEmptySpace(idx)) {
                        changeTurn()
                    }
                }
            }
    }
}
