package nextstep.omok.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import nextstep.omok.OmokContract
import nextstep.omok.R
import nextstep.omok.model.OmokModel
import nextstep.omok.model.Player
import nextstep.omok.presenter.OmokPresenter

class MainActivity : AppCompatActivity(), OmokContract.OmokView {

    private lateinit var presenter: OmokContract.OmokPresenter
    private lateinit var board: TableLayout
    private lateinit var notice: TextView
    private lateinit var rows: List<TableRow>
    private lateinit var imageViews: MutableList<List<ImageView>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = OmokPresenter(activity = this, model = OmokModel())
        board = findViewById(R.id.board)
        notice = findViewById(R.id.notice)

        rows = board.children.filterIsInstance<TableRow>().toList()
        imageViews = mutableListOf()
        rows.forEachIndexed { rowIndex, tableRow ->
            imageViews.add(rowIndex, tableRow.children.filterIsInstance<ImageView>().toList())
        }
        rows.forEachIndexed { rowIndex, _ ->
            imageViews[rowIndex].forEachIndexed { colIndex, imageView ->
                imageView.setOnClickListener {
                    presenter.onIntersectionClick(rowIndex = rowIndex, colIndex = colIndex)
                }
            }
        }

    }

    override fun showTurn(currentTurn: Int, currentPlayer: Player) {
        notice.text = getString(R.string.notice_turn, currentTurn, currentPlayer)
    }

    override fun placeStone(rowIndex: Int, colIndex: Int, playerType: Player) {
        imageViews[rowIndex][colIndex].setImageResource(playerType.resourceId)
    }

    override fun endGame(winner: Player) {
        detachClickListener()
        showWinner(winner)
    }

    private fun detachClickListener() {
        rows.forEachIndexed { rowIndex, _ ->
            imageViews[rowIndex].forEachIndexed { _, imageView ->
                imageView.setOnClickListener(null)
            }
        }
    }

    private fun showWinner(winner: Player) {
        notice.text = getString(R.string.notice_winner, winner)
    }
}
