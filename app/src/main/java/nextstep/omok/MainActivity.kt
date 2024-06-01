package nextstep.omok

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private val board = Array(15) { IntArray(15) { 0 } } // 0: 빈 셀, 1: 검은 돌, 2: 흰 돌
    private var currentPlayer = 1 // 1: 검은 돌, 2: 흰 돌
    private var isGameStarted = false
    private var alreadyPlacedTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boardLayout = findViewById<TableLayout>(R.id.board)
        val startGameText = findViewById<TextView>(R.id.start_game_text)
        alreadyPlacedTextView = findViewById(R.id.intheDol)

        boardLayout?.children?.filterIsInstance<TableRow>()?.forEachIndexed { rowIndex, tableRow ->
            tableRow.children
                .filterIsInstance<ImageView>()
                .forEachIndexed { colIndex, imageView ->
                    imageView.setOnClickListener {
                        if (!isGameStarted) {
                            startGameText?.visibility = View.GONE
                            isGameStarted = true
                        }
                        placePiece(rowIndex, colIndex)
                    }
                }
        }
    }

    override fun <T : View> findViewById(id: Int): T? {
        return super.findViewById(id)
    }

    private fun placePiece(row: Int, col: Int) {
        if (board[row][col] == 0) {
            board[row][col] = currentPlayer
            updateBoard(row, col)
            checkWin(row, col)
            switchPlayer()
        } else {
            showAlreadyPlacedMessage()
        }
    }

    private fun updateBoard(row: Int, col: Int) {
        val boardLayout = findViewById<TableLayout>(R.id.board)
        val imageView = (boardLayout?.getChildAt(row) as? TableRow)?.getChildAt(col) as? ImageView
        imageView?.setImageResource(if (currentPlayer == 1) R.drawable.blackdol else R.drawable.whitedol)
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == 1) 2 else 1
    }

    private fun checkWin(row: Int, col: Int) {
        val player = board[row][col]
        if (player == 0) return

        // 가로, 세로, 대각선 방향 검사
        var count = 1
        if (checkDirection(row, col, 1, 0, player) ||
            checkDirection(row, col, 0, 1, player) ||
            checkDirection(row, col, 1, 1, player) ||
            checkDirection(row, col, 1, -1, player)
        ) {
            showWinnerMessage(player)
        }
    }

    private fun checkDirection(row: Int, col: Int, rowDelta: Int, colDelta: Int, player: Int): Boolean {
        var count = 1
        var i = row + rowDelta
        var j = col + colDelta
        while (i in 0 until 15 && j in 0 until 15) {
            if (board[i][j] == player) {
                count++
                if (count >= 5) {
                    return true
                }
                i += rowDelta
                j += colDelta
            } else {
                break
            }
        }
        i = row - rowDelta
        j = col - colDelta
        while (i in 0 until 15 && j in 0 until 15) {
            if (board[i][j] == player) {
                count++
                if (count >= 5) {
                    return true
                }
                i -= rowDelta
                j -= colDelta
            } else {
                break
            }
        }
        return false
    }

    private fun showWinnerMessage(player: Int) {
        val winnerText = if (player == 1) "검은 돌" else "흰 돌"
        val winnerTextView = findViewById<TextView>(R.id.winnerText)
        winnerTextView?.visibility = View.VISIBLE
        winnerTextView?.text = getString(R.string.winner_text, winnerText)
        isGameStarted = false
    }

    private fun showAlreadyPlacedMessage() {
        alreadyPlacedTextView?.visibility = View.VISIBLE
        alreadyPlacedTextView?.postDelayed({
            alreadyPlacedTextView?.visibility = View.GONE
        }, 3000) // 3초 후에 메시지 숨기기
    }
}




