package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    val BOARD_SIZE = 15
    val OMOK_COUNT = 5

    private lateinit var blackPlayer: Player
    private lateinit var whitePlayer: Player
    private lateinit var currentPlayer: Player
    private var turnCount: Int = 0

    lateinit var board: TableLayout
    lateinit var blackPlayerFlag: ImageView
    lateinit var whitePlayerFlag: ImageView
    lateinit var restartButton: TextView
    lateinit var winnerIs: TextView

    private val boardStatus = Array(BOARD_SIZE) { Array<Stone?>(BOARD_SIZE) { null } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 초기화
        initializeGame()
    }

    private fun initializeGame() {
        initializeViews()
        initializePlayers()
        initializeBoard()
        updatePlayerFlag()
    }

    private fun initializeViews() {
        board = findViewById<TableLayout>(R.id.board)
        blackPlayerFlag = findViewById<ImageView>(R.id.black_player_flag)
        whitePlayerFlag = findViewById<ImageView>(R.id.white_player_flag)
        restartButton = findViewById<TextView>(R.id.restart_button)
        winnerIs = findViewById<TextView>(R.id.winner_is)
        restartButton.visibility = View.GONE
        winnerIs.visibility = View.GONE
    }

    private fun initializePlayers() {
        val blackStone = BlackStone()
        val whiteStone = WhiteStone()
        blackPlayer = Player("Black", blackStone, blackStone.highlightedResId)
        whitePlayer = Player("White", whiteStone, whiteStone.highlightedResId)
        currentPlayer = blackPlayer
    }


    private fun initializeBoard() {
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, tableRow ->
            tableRow.children.filterIsInstance<Cell>().forEachIndexed { colIndex, cell ->
                cell.position = Pair(rowIndex, colIndex)
                cell.setOnClickListener { onCellClick(cell) }
            }
        }
    }

    private fun onCellClick(cell: Cell) {
        val position = cell.position

        if (position != null && cell.isEmpty()) {
            turnCount ++
            val (row, col) = position
            cell.placeStone(currentPlayer.stone)
            boardStatus[row][col] = currentPlayer.stone
            checkGameEnd(position)
        }
    }


    private fun checkGameEnd(position: Pair<Int, Int>) {
        val isWin = checkWinCondition(position)
        val iwDraw = turnCount == BOARD_SIZE * BOARD_SIZE

        if (isWin || iwDraw) {
            val message = if (isWin) "${currentPlayer.stone}이 승리했습니다." else "무승부입니다."
            displayEnding(message)
        } else {
            switchPlayer()
            updatePlayerFlag()
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
            else -> blackPlayer
        }
    }

    private val directions = listOf(
        Direction(1, 0),  // horizontal
        Direction(0, 1),  // vertical
        Direction(1, 1),  // diagonal down-right
        Direction(1, -1)  // diagonal down-left
    )
    private fun checkWinCondition(position: Pair<Int, Int>): Boolean {
        val (row, col) = position
        return directions.any {(dx, dy) ->
            val count = 1 + countStones(row, col, dx, dy) + countStones(row, col, -dx, -dy)
            count >= OMOK_COUNT
        }
    }
    private fun countStones(row: Int, col: Int, dx: Int, dy: Int): Int {
        var nextRow = row + dx
        var nextCol = col + dy

        if (isWithinBounds(nextRow, nextCol) && isSamePlayerStone(nextRow, nextCol)) {
            return 1 + countStones(nextRow,  nextCol, dx, dy)
        }
        return 0
    }

    private fun isWithinBounds(row: Int, col: Int): Boolean {
        return row in 0 until BOARD_SIZE && col in 0 until BOARD_SIZE
    }

    private fun isSamePlayerStone(row: Int, col: Int): Boolean {
        return boardStatus[row][col] == currentPlayer.stone
    }

    private fun displayEnding(message: String) {
        winnerIs.text = message
        restartButton.visibility = View.VISIBLE
        winnerIs.visibility = View.VISIBLE
    }
}