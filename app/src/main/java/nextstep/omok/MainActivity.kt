package nextstep.omok

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private val boardSize = 15
    private var game = OmokGame(boardSize)
    private var currentTurn = Stone.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startNewGame()
    }

    private fun startNewGame() {
        val board = findViewById<TableLayout>(R.id.board)
        board.removeAllViews()
        initializeBoard(board)
        setupBoardClickListeners(board)
    }

    private fun initializeBoard(board: TableLayout) {
        for (i in 0 until boardSize) {
            val tableRow = TableRow(this)
            for (j in 0 until boardSize) {
                val frameLayout = createBoardCell(i, j)
                tableRow.addView(frameLayout)
            }
            board.addView(tableRow)
        }
    }

    private fun createBoardCell(row: Int, col: Int): FrameLayout {
        val frameLayout = FrameLayout(this).apply {
            layoutParams = TableRow.LayoutParams(70, 70)
        }
        val boardImageView = ImageView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setImageResource(getCellDrawable(row, col))
        }
        val stoneImageView = ImageView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setImageResource(android.R.color.transparent) // 초기에는 투명한 이미지로 설정
        }
        frameLayout.addView(boardImageView)
        frameLayout.addView(stoneImageView)
        return frameLayout
    }

    private fun setupBoardClickListeners(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<FrameLayout>()
            .forEachIndexed { index, frameLayout ->
                val x = index % boardSize
                val y = index / boardSize
                setupCellClickListener(frameLayout, x, y)
            }
    }

    private fun setupCellClickListener(frameLayout: FrameLayout, x: Int, y: Int) {
        frameLayout.setOnClickListener {
            handleBoardClick(x, y, frameLayout.getChildAt(1) as ImageView)
        }
    }

    private fun handleBoardClick(x: Int, y: Int, stoneImageView: ImageView) {
        if (game.placeStone(x, y)) {
            updateStoneImage(stoneImageView)
            if (game.checkWin(x, y)) {
                showGameOverDialog()
            } else {
                switchTurn()
            }
        }
    }

    private fun updateStoneImage(stoneImageView: ImageView) {
        stoneImageView.setImageResource(if (currentTurn == Stone.BLACK) R.drawable.black_stone else R.drawable.white_stone)
    }

    private fun switchTurn() {
        currentTurn = if (currentTurn == Stone.BLACK) Stone.WHITE else Stone.BLACK
    }

    private fun showGameOverDialog() {
        AlertDialog.Builder(this)
            .setTitle("게임 종료")
            .setMessage("게임이 종료되었습니다. 새로운 게임을 시작하세요.")
            .setPositiveButton("OK") { _, _ ->
                game = OmokGame(boardSize)
                currentTurn = Stone.BLACK
                startNewGame()
            }
            .setCancelable(false)
            .show()
    }

    private fun getCellDrawable(row: Int, col: Int): Int {
        return when {
            row == 0 && col == 0 -> R.drawable.board_top_left
            row == 0 && col == boardSize - 1 -> R.drawable.board_top_right
            row == boardSize - 1 && col == 0 -> R.drawable.board_bottom_left
            row == boardSize - 1 && col == boardSize - 1 -> R.drawable.board_bottom_right
            row == 0 -> R.drawable.board_top
            row == boardSize - 1 -> R.drawable.board_bottom
            col == 0 -> R.drawable.board_left
            col == boardSize - 1 -> R.drawable.board_right
            else -> R.drawable.board_center
        }
    }
}