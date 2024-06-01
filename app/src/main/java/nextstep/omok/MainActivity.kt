package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    // 현재 턴을 나타내는 변수: true이면 흑돌의 턴, false이면 백돌의 턴
    private var isBlackTurn: Boolean = true

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
                    // 현재 턴에 따라 돌을 놓고, 턴을 변경
                    if (isBlackTurn) {
                        view.setImageResource(R.drawable.black_stone)
                    } else {
                        view.setImageResource(R.drawable.white_stone)
                    }
                    isBlackTurn = !isBlackTurn  // 턴을 변경
                }
            }
    }
}
