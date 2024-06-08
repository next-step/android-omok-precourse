package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private var turn = true
    fun changeTurn(view: ImageView): Boolean {
        if (turn)
            view.setImageResource(R.drawable.black_stone)
        else
            view.setImageResource(R.drawable.white_stone)
        return !turn
    }


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
                view.tag = null
                view.setOnClickListener {
                    if (view.tag == null) {
                        turn = changeTurn(view)
                        view.tag = if (turn) "white" else "black"
                    }


                }

            }
    }
}
