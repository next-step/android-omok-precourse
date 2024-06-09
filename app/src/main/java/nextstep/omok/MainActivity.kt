package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private val BOARD_SIZE = 15
    private val BOARD_ARRAY = Array(BOARD_SIZE) { IntArray(BOARD_SIZE) }
    private var isBlackTurn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeBoard()
    }

    private fun initializeBoard() {
        findViewById<TableLayout>(R.id.board).apply {
            children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
                row.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, view ->
                    view.setOnClickListener { onCellClicked(view, rowIndex, colIndex) }
                    view.setImageResource(0)
                }
            }
        }
    }

    private fun onCellClicked(view: ImageView, rowIndex: Int, colIndex: Int) {
        placeStone(view, rowIndex, colIndex)
        isBlackTurn = !isBlackTurn
    }

    private fun placeStone(view: ImageView, rowIndex: Int, colIndex: Int) {
        BOARD_ARRAY[rowIndex][colIndex] = if (isBlackTurn) 1 else 2
        view.setImageResource(if (isBlackTurn) R.drawable.black_stone else R.drawable.white_stone)
    }
}
