package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private val boardGridCells:MutableList<MutableList<ImageView>> = mutableListOf()
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val table = findViewById<TableLayout>(R.id.board)
        bindBoardGridCellsWithTable(table)

        restoreBoard()
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
        val result = viewModel.clickBoard(x, y) ?: return
        applyStonePlacementToGrid(result)
    }

    private fun restoreBoard(){
        val placementList = viewModel.getStonePlacementList()
        for(placement in placementList) {
            applyStonePlacementToGrid(placement)
        }
    }

    private fun checkPlacementFeasibility(x: Int, y:Int): Boolean {
        if (y < 0 || y >= boardGridCells.count()) {
            return false
        }
        if (x < 0 || x >= boardGridCells[y].count()) {
            return false
        }
        return true
    }

    private fun applyStonePlacementToGrid(placement: StonePlacement) {
        if (!checkPlacementFeasibility(placement.x, placement.y)) {
            Log.e("MainActivity Error", "index out of range")
            return
        }

        when (placement.after) {
            Board.STONE_BLACK -> boardGridCells[placement.y][placement.x].setImageResource(R.drawable.black_stone)
            Board.STONE_WHITE -> boardGridCells[placement.y][placement.x].setImageResource(R.drawable.white_stone)
            Board.STONE_EMPTY -> boardGridCells[placement.y][placement.x].setImageDrawable(null)
            else -> Log.e("MainActivity Error", "invalid type of stone on placement")
        }
    }
}
