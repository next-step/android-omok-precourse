package nextstep.omok

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
class MainActivity : AppCompatActivity() {
    private val gameManager = GameManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        setupBoard(board)
    }

    private fun setupBoard(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                setupRow(row, rowIndex)
            }
    }

    private fun setupRow(row: TableRow, rowIndex: Int) {
        row.children
            .filterIsInstance<ImageView>()
            .forEachIndexed { colIndex, view ->
                setupCell(view, rowIndex, colIndex)
            }
    }
    private fun setupCell(view: ImageView, rowIndex: Int, colIndex: Int) {
        view.setOnClickListener {
            handleCellClick(rowIndex, colIndex)
        }
    }

    private fun handleCellClick(rowIndex: Int, colIndex: Int) {
        val stone = gameManager.placeStone(rowIndex, colIndex)
        if (stone != 0) {
            val imageView =
                (findViewById<TableLayout>(R.id.board).getChildAt(rowIndex) as TableRow).getChildAt(
                    colIndex
                ) as ImageView
            imageView.setImageResource(stone)
            checkWin(gameManager.checkWin(stone, rowIndex, colIndex), stone)
            checkBoardisFull(gameManager.isBoardFull())

        } else { // 돌이 이미 있는 경우
            showToast()
        }
    }

    private fun showToast() {
        Toast.makeText(this, "잘못된 위치!", Toast.LENGTH_SHORT).show()
    }

    private fun showReGameAlert(msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(msg)
        builder.setMessage("게임 재시작?")
        builder.setPositiveButton("예", DialogInterface.OnClickListener { dialog, which ->
            gameManager.resetGame()
            resetBoard()
        })
        builder.setNegativeButton("아니오", DialogInterface.OnClickListener { dialog, which ->
            finish()
        })
        builder.create()
        builder.show()
    }

    private fun checkWin(isWon: Boolean, stone: Int) {
        if (isWon) {
            val msg = ""+gameManager.getStoneName(stone)+" 우승!"
            showReGameAlert(msg)
        }
    }

    private fun checkBoardisFull(isFull: Boolean) {
        if(isFull) {
            val msg = "공간 부족! 무승부입니다"
            showReGameAlert(msg)
        }
    }
    private fun resetBoard() {
        val size = gameManager.getSize()
        val boardLayout = findViewById<TableLayout>(R.id.board)
        for (i in 0 until size) {
            val row = boardLayout.getChildAt(i) as TableRow
            for (j in 0 until size) {
                val imageView = row.getChildAt(j) as ImageView
                imageView.setImageResource(0) // 이미지 초기화
            }
        }
    }
}
