package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private var turn = true
    private var boardSize: Int = 15
    private var boardStoneColor = Array(boardSize) { Array<String?>(boardSize) { null } }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setOnClickListener {
                    val row = (view.parent as? TableRow)?.indexOfChild(view) ?: -1
                    val column =
                        (view.parent.parent as? TableLayout)?.indexOfChild(view.parent as? TableRow) ?: -1
                    if (stoneColor(row, column) == null) {
                        turn = changeTurn(view)
                        boardStoneColor[row][column] = if (turn) "white" else "black"
                    }
                }

            }
    }

    private fun changeTurn(view: ImageView): Boolean {
        if (turn)
            view.setImageResource(R.drawable.black_stone)
        else
            view.setImageResource(R.drawable.white_stone)
        return !turn
    }

    private fun stoneColor(row: Int, column: Int): String? {
        return boardStoneColor[row][column]
    }
}
