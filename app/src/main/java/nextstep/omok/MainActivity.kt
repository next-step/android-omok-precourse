package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private var freePlay: Boolean = false
    private val boardGridCells: MutableList<MutableList<ImageView>> = mutableListOf()
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.newgame_button).setOnClickListener { clickNewGameButton() }
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

    private fun checkOmok(x: Int, y: Int): Boolean = viewModel.checkOmok(x, y)

    private fun notifyWinner(x: Int, y: Int) {
        val stoneColor = viewModel.getStone(x, y)
        if (stoneColor == Board.STONE_BLACK) {
            showDialog("흑돌 승!")
        } else {
            showDialog("백돌 승!")
        }
    }

    private fun clickTable(x: Int, y: Int) {
        val result = viewModel.clickBoard(x, y) ?: return

        applyStonePlacementToGrid(result)
        if (!freePlay && checkOmok(x, y)) {
            viewModel.gameActive = false
            notifyWinner(x, y)
        }
    }

    private fun clearTable() {
        for (row in boardGridCells) {
            for (cell in row) {
                cell.setImageDrawable(null)
            }
        }
    }

    private fun restoreBoard() {
        val placementList = viewModel.getStonePlacementList()
        for (placement in placementList) {
            applyStonePlacementToGrid(placement)
        }
    }

    private fun checkPlacementFeasibility(x: Int, y: Int): Boolean {
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
            Board.STONE_BLACK -> boardGridCells[placement.y][placement.x]
                .setImageResource(R.drawable.black_stone)

            Board.STONE_WHITE -> boardGridCells[placement.y][placement.x]
                .setImageResource(R.drawable.white_stone)

            Board.STONE_EMPTY -> boardGridCells[placement.y][placement.x]
                .setImageDrawable(null)
        }
    }

    private fun startNewGame(){
        freePlay = false
        viewModel.reset()
        clearTable()
        restoreBoard()
    }

    private fun clickNewGameButton() {
        startNewGame()
    }

    private fun showDialog(text: String) {
        val dialog = GameResultDialog(object : GameResultDialogInterface {
            override fun onNewGameButtonClick() {
                startNewGame()
            }

            override fun onContinueButtonClick() {
                viewModel.gameActive = true
                freePlay = true
            }
        }, text)
        dialog.isCancelable = false
        dialog.show(this.supportFragmentManager, "GameResultDialog")
    }
}
