package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private var turn = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setOnClickListener {
                placeStone(view)
                checkWin()
                checkGameOver()
            } }

    }
    private fun placeStone(view: ImageView) {
        if (view.drawable != null) return

        val stone = if (turn % 2 == 0) R.drawable.black_stone else R.drawable.white_stone
        view.setImageResource(stone)

        addTurn()
    }

    private fun addTurn() {
        turn++
    }

    private fun checkWin() {
    }

    private fun checkGameOver() {
    }

}
