package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private lateinit var controller: OmokController
    private lateinit var blackTurnInfo: LinearLayout
    private lateinit var whiteTurnInfo: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blackTurnInfo = findViewById(R.id.black_turn_info)
        whiteTurnInfo = findViewById(R.id.white_turn_info)
        controller = OmokController(this)

        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.setOnClickListener { view.setImageResource(R.drawable.black_stone) } }
    }

    fun updateTurnInfo(curPlayer: Player) {
        when (curPlayer) {
            Player.BLACK -> {
                blackTurnInfo.setBackgroundResource(R.drawable.background_highlight)
                whiteTurnInfo.setBackgroundResource(R.drawable.background_basic)
            }
            Player.WHITE -> {
                blackTurnInfo.setBackgroundResource(R.drawable.background_basic)
                whiteTurnInfo.setBackgroundResource(R.drawable.background_highlight)
            }
            else -> {
                blackTurnInfo.setBackgroundResource(R.drawable.background_basic)
                whiteTurnInfo.setBackgroundResource(R.drawable.background_basic)
            }
        }
    }
}
