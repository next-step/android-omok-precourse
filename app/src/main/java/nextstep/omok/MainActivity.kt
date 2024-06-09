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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeBoard()
    }

    private fun initializeBoard() {
        findViewById<TableLayout>(R.id.board).apply {
            children.filterIsInstance<TableRow>().forEach { row ->
                row.children.filterIsInstance<ImageView>().forEach {view ->
                    view.setImageResource(0)
                }
            }
        }
    }
}
