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

                    changeTurn()

                }
            }
    }
}
