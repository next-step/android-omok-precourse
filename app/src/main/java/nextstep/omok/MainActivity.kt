package nextstep.omok

import android.graphics.Path.Direction
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    companion object {
        const val BOARD_SIZE = 15
        const val OMOK_COUNT = 5
    }

    val blackStone = BlackStone()
    val whiteStone = WhiteStone()

    val blackPlayer = Player("Black", blackStone, blackStone.highlightedResId)
    val whitePlayer = Player("White", whiteStone, whiteStone.highlightedResId)

    var currentPlayer: Player = blackPlayer
    lateinit var board: TableLayout
    lateinit var blackPlayerFlag: ImageView
    lateinit var whitePlayerFlag: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = findViewById(R.id.board)
        blackPlayerFlag = findViewById(R.id.black_player_flag)
        whitePlayerFlag = findViewById(R.id.white_player_flag)

        initializeBoard(board)
        updatePlayerFlag()
    }

    private fun initializeBoard(board: TableLayout) {
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, tableRow ->
            tableRow.children.filterIsInstance<Cell>().forEachIndexed { colIndex, cell ->
                cell.position = Pair(rowIndex, colIndex)
                cell.setOnClickListener { onCellClick(cell) }
            }
        }
    }

    private fun onCellClick(cell: Cell) {

        val position = cell.position
        if (position != null) {
            if (cell.isEmpty()) {
                cell.placeStone(currentPlayer.stone)
                Log.d("gamee", "${currentPlayer.name} turn :  ${position.first} ${position.second}")
                if (checkWinCondition(position.first, position.second)) {
                    Log.d("gamee", "${currentPlayer.name} wins!")
                } else {
                    switchPlayer()
                    updatePlayerFlag()
                }
            }
        }
    }


    private fun updatePlayerFlag() {
        when (currentPlayer) {
            blackPlayer -> {
                blackPlayerFlag.setImageResource(blackPlayer.playerResId)
                whitePlayerFlag.setImageResource(whitePlayer.stone.resId)
            }
            whitePlayer -> {
                blackPlayerFlag.setImageResource(blackPlayer.stone.resId)
                whitePlayerFlag.setImageResource(whitePlayer.playerResId)
            }
        }
    }

    private fun switchPlayer() {
        currentPlayer = when (currentPlayer) {
            blackPlayer -> whitePlayer
            whitePlayer -> blackPlayer
            else -> currentPlayer
        }
    }

    private fun checkWinCondition(row: Int, col: Int): Boolean {
        val directions = listOf(
            Pair(1, 0), // horizontal
            Pair(0, 1), // vertical
            Pair(1, 1), // diagonal down-right
            Pair(1, -1), // diagonal down-left
        )
        for (direction in directions) {
            val count = 1 + countStones(row, col, direction.first, direction.second) +
                    countStones(row, col, -direction.first, -direction.second)
            Log.d("gamee", "checkWinCondition : Count in direction ${direction}: $count")
            if (count >= OMOK_COUNT) {
                return true
            }
        }
        return false
    }

    private fun countStones(row: Int, col: Int, rowDir: Int, colDir: Int): Int {
        val nextRow = row + rowDir
        val nextCol = col + colDir
        if (isWithinBounds(nextRow, nextCol) && isSamePlayerStone(nextRow, nextCol)) {
            val count = 1 + countStones(nextRow, nextCol, rowDir, colDir)
            Log.d("gamee", "countStones : ($nextRow, $nextCol) in direction ($rowDir, $colDir): $count")
            return count
        }
        return 0
    }

    private fun isWithinBounds(row: Int, col: Int): Boolean {
        return row in 0 until BOARD_SIZE && col in 0 until BOARD_SIZE
    }

    private fun isSamePlayerStone(row: Int, col: Int): Boolean {
        val cell = board.findViewById<Cell>(resources.getIdentifier("cell_${row}_${col}", "id", packageName))
        val result = cell?.currentStone == currentPlayer.stone
        Log.d("gamee", "isSamePlayerStone : Checking stone at ($row, $col): $result")
        return result
    }
}
