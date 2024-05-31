package nextstep.omok

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children

class OmokSystem (
    val context: Context,
    val board: TableLayout) {
    private val BLACK = "흑"
    private val WHITE = "백"
    private val scoreCombo = 5

    private var nowStone = BLACK
    private val cache = Cache()
    fun registerClickListener() {
        board.children.forEachIndexed { row, tableRow ->
            tableRow as TableRow
            tableRow.children.forEachIndexed { col, imgView ->
                imgView as ImageView
                imgView.setOnClickListener {
                    performTurn(row, col)
                }
            }
        }
    }

    // 이미지뷰를 터치했을 때 수행할 한턴의 동작
    private fun performTurn(row: Int, col: Int) {
        putStone(row, col)
    }

    // 돌을 착수하는 함수
    // performTurn 내부에서 호출
    private fun putStone(row: Int, col: Int) {
        val currView = getBoardImageView(row, col)
        if (isPositionEmpty(currView)) {
            if (nowStone == WHITE) {
                currView.setImageDrawable(cache.stoneCache[cache.STONE_WHITE])
                nowStone = BLACK
            } else {
                currView.setImageDrawable(cache.stoneCache[cache.STONE_BLACK])
                nowStone = WHITE
            }
        }
    }

    // 보드의 row, col 위치에 존재하는 imageView 반환
    private fun getBoardImageView(row: Int, col: Int): ImageView {
        return (board.getChildAt(row) as TableRow).getChildAt(col) as ImageView
    }

    // 돌을 놓으려는 위치가 비어있는지 확인하는 함수
    // putStone 내부에서 호출
    private fun isPositionEmpty(imgView: ImageView): Boolean {
        if (imgView.drawable == null)
            return true
        else
            Toast.makeText(context, "이미 돌이 착수된 자리입니다!", Toast.LENGTH_SHORT).show()
            return false
    }

    // 돌을 착수 할 때마다 Drawable을 얻어오는 동작을 줄이기 위해 캐시로 갖고있는 클래스
    // OmokSystem 객체가 생성될 때 내부에서 생성
    private inner class Cache() {
        // 돌 관련 상수
        val STONE_BLACK = R.drawable.black_stone
        val STONE_WHITE = R.drawable.white_stone

        // 캐시
        val stoneCache: HashMap<Int, Drawable?>

        init {
            stoneCache = createStoneCache()
        }

        private fun createStoneCache(): HashMap<Int, Drawable?> {
            val res = HashMap<Int, Drawable?>()

            res[STONE_WHITE] = ResourcesCompat.getDrawable(context.resources, STONE_WHITE, null)
            res[STONE_BLACK] = ResourcesCompat.getDrawable(context.resources, STONE_BLACK, null)

            return res
        }
    }
}