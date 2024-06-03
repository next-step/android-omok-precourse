package nextstep.omok

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    lateinit var board: TableLayout
    var presenter: GameManager = GameManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = findViewById<TableLayout>(R.id.board)
        restartOnClickListener()
        boardOnClickListener()
        finishButtonOnClickListener()
    }

    fun restartOnClickListener() {
        findViewById<TextView>(R.id.restartButton).setOnClickListener {
            presenter.initGame()
        }
    }

    fun finishButtonOnClickListener() {
        findViewById<TextView>(R.id.endButton).setOnClickListener {
            finish()
        }
    }

    fun boardOnClickListener() {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setOnClickListener {
                    val position = getClickPosition(view)
                    val player =
                        presenter.playOneTurnWithExceptionHandler(view, position[0], position[1])
                }
            }
    }

    fun getClickPosition(view: ImageView): List<Int> {
        val row = view.parent as TableRow
        val rowIndex = row.indexOfChild(view)
        val columnIndex = board.indexOfChild(row)
        return listOf(rowIndex, columnIndex)
    }

    fun drawStone(view: ImageView, player: Int) {
        when (player) {
            1 -> view.setImageResource(R.drawable.black_stone)
            -1 -> view.setImageResource(R.drawable.white_stone)
            else -> Log.d("testt", "error")
        }
    }

    fun restartBoardView() {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setImageResource(0)
            }
    }

    fun showGameOver(winner: Int) {
        val winnerStone: String = if (winner == 1) "검은 돌" else "하얀 돌"
        AlertDialog.Builder(this)
            .setTitle("게임 끝")
            .setMessage("${winnerStone} 플레이어가 승리했습니다!")
            .setPositiveButton("Restart", DialogInterface.OnClickListener { dialog, which ->
                presenter.initGame()
            })
            .setNegativeButton("Exit", DialogInterface.OnClickListener { dialog, which ->
                finish()
            })
            .show()
    }
}
