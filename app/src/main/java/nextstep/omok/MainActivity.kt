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

    /**
     * 변수와 뷰를 초기화.
     * - `board`: 오목 보드 뷰.
     * - `blackTurnImageView`: 흑돌 차례를 나타내는 이미지 뷰.
     * - `whiteTurnImageView`: 백돌 차례를 나타내는 이미지 뷰.
     * - `placeStoneBtn`: 돌을 보드에 두는 버튼.
     */
    private fun initVariables() {
        board = findViewById(R.id.board)
        blackTurnImageView = findViewById(R.id.black_turn_image)
        whiteTurnImageView = findViewById(R.id.white_turn_image)
        placeStoneBtn = findViewById(R.id.place_stone_btn)
    }


    /**
     * 보드의 각 칸(이미지 뷰)에 클릭 리스너 설정.
     * 칸을 클릭하면 해당 칸이 맞는 지 미리 보여주는 동작 수행.
     */
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

    /**
     * 칸을 클릭하면 해당 칸이 맞는 지 미리 보여주는 동작 수행.
     * 놓을 위치를 변경 하고자 할 땐 이전 칸의 미리 보기 삭제.
     * 칸에 돌이 없을 때만 동작.
     *
     * @param cell 클릭한 칸을 나타내는 이미지 뷰
     */
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


    /**
     * '두기' 버튼에 대한 클릭 리스너 설정.
     * 돌을 둘 칸을 선택한 경우 돌을 둠.
     * 돌을 둘 칸을 선택하지 않은 경우 안내 메시지를 Toast.
     */
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

    /**
     * 차례에 맞게 선택한 칸에 돌을 두는 함수.
     * 돌을 두고 나면 차례를 바꿈.
     */
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
