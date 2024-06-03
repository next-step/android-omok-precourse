package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

sealed class Result {
    object Success : Result()
    data class Failure(val reason: String) : Result()
}

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "흑돌"
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeGame()
    }

    private fun initializeGame() {
        showMessage("흑돌 차례입니다.")
        setupBoard()
    }

    private fun setupBoard() {
        val board = findViewById<TableLayout>(R.id.board)
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { imageView ->
                imageView.setOnClickListener { view ->
                    if (gameActive) placeStone(view as ImageView)
                }
            }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
