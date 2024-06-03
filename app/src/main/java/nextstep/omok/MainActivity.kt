package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
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

}
