package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private var turn = 0
    private var table = Array(15) { IntArray(15) { 0 } }
    val board = findViewById<TableLayout>(R.id.board)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBoard()
    }

    private fun setupBoard() {}

    private fun placeStone() {}

    private fun updateBoardState() {}

    private fun countStones() {}

    private fun countDirection() {}

    private fun checkWin() {}

    private fun handleWin() {}

    private fun showWinDialog() {}

}

