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
    private lateinit var board: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blackTurnInfo = findViewById(R.id.black_turn_info)
        whiteTurnInfo = findViewById(R.id.white_turn_info)
        controller = OmokController(this)

        board = findViewById<TableLayout>(R.id.board)
        addClickListerToBoard(board)
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

    private fun addClickListerToBoard(omokBoard: TableLayout) {
        omokBoard
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { r, omokRow ->
                addClickListenerToTableRow(omokRow, r)
            }
    }

    private fun addClickListenerToTableRow(omokRow: TableRow, r: Int) {
        omokRow
            .children
            .filterIsInstance<ImageView>()
            .forEachIndexed { c, omokCell ->
                addClickListenerToCell(omokCell, r, c)
            }
    }

    private fun addClickListenerToCell(omokCell: ImageView, r: Int, c: Int) {
        omokCell.setOnClickListener {
            controller.tryPutStone(r, c)
        }
    }

    fun updateBoardCell(r: Int, c: Int, player: Player) {
        val targetRow: TableRow = board.getChildAt(r) as TableRow
        val targetCell: ImageView = targetRow.getChildAt(c) as ImageView

        when (player) {
            Player.BLACK -> targetCell.setImageResource(R.drawable.black_stone)
            Player.WHITE -> targetCell.setImageResource(R.drawable.white_stone)
            else -> targetCell.setImageResource(0)
        }
    }
}
