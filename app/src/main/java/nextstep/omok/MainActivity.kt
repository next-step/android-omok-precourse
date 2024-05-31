package nextstep.omok

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var board: Board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBoard()
    }

    private fun setupBoard() {
        val tableLayout = findViewById<TableLayout>(R.id.board)
        board = Board(tableLayout, ::onCellClicked)
        board.initialize()
    }
}
