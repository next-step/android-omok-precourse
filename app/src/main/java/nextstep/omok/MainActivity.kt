package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private var isBlackTurn = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
    }

    private fun setView() {
        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setOnClickListener{ putStoneInTurn(view) } }
    }

    private fun putStoneInTurn(imageView: ImageView) {
        if (imageView.tag == null) {
            if (isBlackTurn) {
                imageView.setImageResource(R.drawable.black_stone)
            } else {
                imageView.setImageResource(R.drawable.white_stone)
            }
            imageView.tag = isBlackTurn
            isBlackTurn = !isBlackTurn
        }
    }
}
