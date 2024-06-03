package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    lateinit var board :TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = findViewById<TableLayout>(R.id.board)
        boardOnClickListener()
    }

    fun boardOnClickListener(){
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setOnClickListener {
                    val position = getClickPosition(view)
                }
            }
    }

    fun getClickPosition(view : ImageView) : List<Int>{
        val row = view.parent as TableRow
        val rowIndex = row.indexOfChild(view)
        val columnIndex = board.indexOfChild(row)
        return listOf(rowIndex, columnIndex)
    }
}
