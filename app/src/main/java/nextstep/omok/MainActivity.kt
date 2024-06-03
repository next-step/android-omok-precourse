package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
class MainActivity : AppCompatActivity() {
    private val gameManager = GameManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        setupBoard(board)
    }

    private fun setupBoard(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                setupRow(row, rowIndex)
            }
    }

    private fun setupRow(row: TableRow, rowIndex: Int) {
        row.children
            .filterIsInstance<ImageView>()
            .forEachIndexed { colIndex, view ->
                setupCell(view, rowIndex, colIndex)
            }
    }

    private fun setupCell(view: ImageView, rowIndex: Int, colIndex: Int) {
        view.setOnClickListener {
            handleCellClick(rowIndex, colIndex)
        }
    }

    private fun handleCellClick(rowIndex: Int, colIndex: Int) {
        val stone = gameManager.placeStone(rowIndex, colIndex)
        if (stone != 0) {
            val imageView =
                (findViewById<TableLayout>(R.id.board).getChildAt(rowIndex) as TableRow).getChildAt(
                    colIndex
                ) as ImageView
            imageView.setImageResource(stone)
        } else {
            showToast()
        }
    }
    
    private fun showToast() {
        Toast.makeText(this, "잘못된 위치!", Toast.LENGTH_SHORT).show()
    }
    }
