package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    val boardSize = 15
    lateinit var board: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = findViewById(R.id.board)
        initializeBoard(board)
    }

    private fun initializeBoard(board: TableLayout) {
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                tableRow.children
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { colIndex, imageView ->
                        imageView.tag = Pair(rowIndex, colIndex)
                        imageView.setOnClickListener { onCellClick(imageView) }
                    }
            }
    }

    private fun onCellClick(cell: ImageView) {
        // 태그에서 좌표 정보를 추출하여 로그 출력
        val position = cell.tag as Pair<Int, Int>
        Log.d("testt", "Row: ${position.first}, Column: ${position.second}")
    }
}
