package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private var currentStone = "b"  //black
    private val boardSate = Array(15) { Array<String?>(15) { null } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        initializeBoard(board)
    }

    private fun initializeBoard(board:TableLayout){
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setOnClickListener { placeStone(view) } }
    }

    private fun placeStone(view:ImageView) {
        if (currentStone == "b") {
            view.setImageResource(R.drawable.black_stone)
            currentStone = "w"
        } else {
        view.setImageResource(R.drawable.white_stone)
        currentStone = "b"}
    }
}
