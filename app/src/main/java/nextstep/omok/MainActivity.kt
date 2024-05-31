package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    // 오목 보드
    private lateinit var board: Array<Array<ImageView?>>
    // 15x15 보드 사이즈
    private val boardSize = 15
    // 현재 플레이어(black or white)
    private var currentPlayer = "black"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tableLayout = findViewById<TableLayout>(R.id.board)
        board = Array(boardSize) { arrayOfNulls(boardSize) }
        var rowIndex = 0 // 세로
        tableLayout.children.filterIsInstance<TableRow>().forEach { row ->
                var colIndex = 0 // 가로
                row.children.filterIsInstance<ImageView>().forEach { view ->
                        view.setOnClickListener { onStonePlaced(view) } // 클릭 시 돌 놓기
                        view.tag = "$rowIndex,$colIndex"  // 태그에 세로, 가로 설정
                        board[rowIndex][colIndex] = view
                        colIndex++ }
                rowIndex++ }
    }


}
