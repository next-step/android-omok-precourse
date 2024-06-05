package nextstep.omok

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children


// GameView.kt
interface GameView {
    fun initializeBoard()
    fun updatePlayerFlag(player: Player)
    fun displayEnding(message: String)
    fun onCellClick(cell: Cell)
}

class MainActivity : AppCompatActivity(), GameView {

    private lateinit var gameEngine: GameEngine
    private lateinit var board: TableLayout
    private lateinit var blackPlayerFlag: ImageView
    private lateinit var whitePlayerFlag: ImageView
    private lateinit var restartButton: TextView
    private lateinit var winnerIs: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 초기화
        initializeViews()
        gameEngine = GameEngine(this)
        gameEngine.startGame()
    }

    private fun initializeViews() {
        board = findViewById(R.id.board)
        blackPlayerFlag = findViewById(R.id.black_player_flag)
        whitePlayerFlag = findViewById(R.id.white_player_flag)
        restartButton = findViewById(R.id.restart_button)
        winnerIs = findViewById(R.id.winner_is)
        winnerIs.visibility = View.GONE
        restartButton.visibility = View.GONE
        restartButton.setOnClickListener {
            gameEngine.resetGame()
            restartButton.visibility = View.GONE
            winnerIs.visibility = View.GONE
        }
    }

    override fun initializeBoard() {
        board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, tableRow ->
            tableRow.children.filterIsInstance<ImageViewCell>().forEachIndexed { colIndex, cell ->
                cell.position = Pair(rowIndex, colIndex)
                cell.setOnClickListener { onCellClick(cell) }
                cell.setImageResource(android.R.color.transparent)
            }
        }
    }

    override fun onCellClick(cell: Cell) {
        if (gameEngine.isGameOver()) return

        val imageViewCell = cell as? ImageViewCell
        val position = imageViewCell?.position
        if (position != null && imageViewCell.isEmpty()) {
            imageViewCell.placeStone(gameEngine.currentPlayer.stone)
            gameEngine.handleCellClick(imageViewCell)
        }
    }


    override fun updatePlayerFlag(player: Player) {
        when (player) {
             gameEngine.blackPlayer -> {
                blackPlayerFlag.setImageResource(player.playerResId)
                whitePlayerFlag.setImageResource(gameEngine.whitePlayer.stone.resId)
            }
            gameEngine.whitePlayer -> {
                blackPlayerFlag.setImageResource(gameEngine.blackPlayer.stone.resId)
                whitePlayerFlag.setImageResource(player.playerResId)
            }
        }
    }

    override fun displayEnding(message: String) {
        winnerIs.text = message
        restartButton.visibility = View.VISIBLE
        winnerIs.visibility = View.VISIBLE
    }
}