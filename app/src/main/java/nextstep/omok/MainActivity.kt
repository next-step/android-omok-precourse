package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private val boardGridCells:MutableList<MutableList<ImageView>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val table = findViewById<TableLayout>(R.id.board)

        bindBoardGridCellsWithTable(table)
    }

    private fun bindBoardGridCellsWithTable(table: TableLayout) {
        table
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { index, row -> initiateBoardGridRow(index, row) }
    }

    private fun initiateBoardGridRow(rowIndex: Int, tableRow: TableRow) {
        boardGridCells.add(mutableListOf())
        tableRow.children.filterIsInstance<ImageView>().forEachIndexed { index, view ->
            boardGridCells.last().add(view)
            view.setOnClickListener { clickTable(index, rowIndex) }
        }
    }

    private fun clickTable(x: Int, y:Int) {
        if((x+y) % 2 == 0){
            boardGridCells[y][x].setImageResource(R.drawable.white_stone)
        }
        else{
            boardGridCells[y][x].setImageResource(R.drawable.black_stone)
        }
    }
}
