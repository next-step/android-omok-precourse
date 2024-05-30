package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    var isBlackTurn = true
    var readyToPlace = false
    lateinit var board: TableLayout
    lateinit var blackTurnImageView: ImageView
    lateinit var whiteTurnImageView: ImageView
    lateinit var placeStoneBtn: Button
    lateinit var cellToPlace: ImageView
    lateinit var prevCellToPlace: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVariables()
        setOnClickListenerForStones()
        setOnClickListenerForPlaceStoneBtn()
    }


    private fun initVariables() {
        board = findViewById(R.id.board)
        blackTurnImageView = findViewById(R.id.black_turn_image)
        whiteTurnImageView = findViewById(R.id.white_turn_image)
        placeStoneBtn = findViewById(R.id.place_stone_btn)
    }

    private fun setOnClickListenerForStones() {
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { cell ->
                cell.setOnClickListener {
                    previewStone(cell)
                }
            }
    }

    private fun previewStone(cell: ImageView) {
        if (readyToPlace) {
            prevCellToPlace.setImageDrawable(null)
        }
        if (cell.drawable == null) {
            cell.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dashed_stone))
            readyToPlace = true
            cellToPlace = cell
            prevCellToPlace = cell
        }
    }

    private fun setOnClickListenerForPlaceStoneBtn() {
        placeStoneBtn.setOnClickListener {
            if (readyToPlace) {
                placeStone()
                readyToPlace = false
            } else {
                Toast.makeText(this, "먼저 돌을 둘 곳을 클릭하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun placeStone() {
        if (isBlackTurn) {
            cellToPlace.setImageResource(R.drawable.black_stone)
            blackTurnImageView.visibility = View.INVISIBLE
            whiteTurnImageView.visibility = View.VISIBLE
        } else {
            cellToPlace.setImageResource(R.drawable.white_stone)
            whiteTurnImageView.visibility = View.INVISIBLE
            blackTurnImageView.visibility = View.VISIBLE
        }
        isBlackTurn = !isBlackTurn
    }

}
