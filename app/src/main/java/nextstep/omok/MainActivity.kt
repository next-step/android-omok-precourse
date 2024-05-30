package nextstep.omok

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    var isBlackTurn = true
    lateinit var board: TableLayout
    lateinit var blackTurnImageView: ImageView
    lateinit var whiteTurnImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVariables()
        setOnClickListenerForStones()
    }

    private fun initVariables() {
        board = findViewById(R.id.board)
        blackTurnImageView = findViewById(R.id.black_turn_image)
        whiteTurnImageView = findViewById(R.id.white_turn_image)
    }

    private fun setOnClickListenerForStones() {
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setOnClickListener {
                    placeStone(view)
                }
            }
    }

    private fun placeStone(viewToPlace: ImageView) {
        if (isBlackTurn) {
            viewToPlace.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dashed_stone))
            //viewToPlace.setImageResource(R.drawable.black_stone)
            blackTurnImageView.visibility = View.INVISIBLE
            whiteTurnImageView.visibility = View.VISIBLE
        } else {
            viewToPlace.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dashed_stone))
            //viewToPlace.setImageResource(R.drawable.white_stone)
            whiteTurnImageView.visibility = View.INVISIBLE
            blackTurnImageView.visibility = View.VISIBLE
        }
        isBlackTurn = !isBlackTurn
    }

}
