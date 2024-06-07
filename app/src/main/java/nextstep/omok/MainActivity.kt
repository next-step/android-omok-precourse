package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

const val PLAYER_BLACK = 'B'
const val PLAYER_WHITE = 'W'

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeBoard()
        initializePlayer()
        setCellClickListener()
    }

    private fun initializeBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        board.children
            .filterIsInstance<TableRow>()
            .forEach { row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEach { view ->
                        view.setImageResource(0)
                        view.tag = null
                    }
            }
    }

    private fun initializePlayer() {
        var currentPlayer = PLAYER_BLACK
    }

    private fun setCellClickListener() {
        val board = findViewById<TableLayout>(R.id.board)
        board.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                row.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, view ->
                        view.setOnClickListener { processClickedCell(rowIndex, colIndex) }
                    }
            }
    }

    private fun processClickedCell(row: Int, col: Int) {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
    }
}