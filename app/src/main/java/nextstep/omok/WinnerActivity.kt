package nextstep.omok

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WinnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val winnerMessage = intent.getStringExtra("winnerMessage") ?: "Unknown winner"
        val winnerTextView = findViewById<TextView>(R.id.winnerTextView)
        winnerTextView.text = winnerMessage
    }
}