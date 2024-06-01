package nextstep.omok

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.core.view.size

class OmokSystem (
    val context: Context,
    val board: TableLayout) {
    private val BLACK = "흑"
    private val WHITE = "백"
    private val scoreCombo = 5
    private val rowSize: Int
    private val colSize: Int

    private var nowStone = BLACK
    private val cache = Cache()
    init {
        rowSize = board.childCount
        colSize = (board.getChildAt(0) as TableRow).childCount
    }

    // 각 이미지뷰에 onClickListener를 부착하는 함수
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
        val combo = calculateCombo(row, col)
        if (isGameEnd(combo))
            alertEnd(row, col)
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
        else {
            Toast.makeText(context, "이미 돌이 착수된 자리입니다!", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    // 콤보를 계산하는 함수
    // 4가지 방향에 대해 가장 큰 콤보를 반환
    private fun calculateCombo(row: Int, col: Int): Int {
        return maxOf(
            checkVerticalCombo(row, col),
            checkHorizontalCombo(row, col),
            checkUpperRightCombo(row, col),
            checkUpperLeftCombo(row, col)
        )
    }

    // 4가지 방향 각각에 대해 콤보를 계산하는 함수
    // 각각의 함수는 calculateCombo함수 내부에서 호출되어 사용
    // 점수를 내는 기준인 scoreCombo개수까지만 인접한 돌을 셈
    // 수직 방향
    private fun checkVerticalCombo(row: Int, col: Int) : Int {
        var combo = 1
        val targetStone = getBoardImageView(row, col)

        for (d in 1..<scoreCombo) {
            if (row - d < 0 ||
                targetStone.drawable != getBoardImageView(row - d, col).drawable)
                break
            combo++
        }
        for (d in 1..<scoreCombo) {
            if (row + d >= rowSize ||
                targetStone.drawable != getBoardImageView(row + d, col).drawable)
                break
            combo++
        }

        return combo
    }

    // 수평 방향
    private fun checkHorizontalCombo(row: Int, col: Int) : Int {
        var combo = 1
        val targetStone = getBoardImageView(row, col)

        for (d in 1..<scoreCombo) {
            if (col - d < 0 ||
                targetStone.drawable != getBoardImageView(row, col - d).drawable)
                break
            combo++
        }
        for (d in 1..<scoreCombo) {
            if (col + d >= colSize ||
                targetStone.drawable != getBoardImageView(row, col + d).drawable)
                break
            combo++
        }

        return combo
    }

    // 오른쪽 위를 향하는 대각선 방향
    private fun checkUpperRightCombo(row: Int, col: Int) : Int {
        var combo = 1
        val targetStone = getBoardImageView(row, col)

        for (d in 1..<scoreCombo) {
            if (col - d < 0 || row + d >= rowSize ||
                targetStone.drawable != getBoardImageView(row + d, col - d).drawable)
                break
            combo++
        }
        for (d in 1..<scoreCombo) {
            if (col + d >= colSize || row - d < 0 ||
                targetStone.drawable != getBoardImageView(row - d, col + d).drawable)
                break
            combo++
        }

        return combo
    }

    // 왼쪽 위를 향하는 대각선 방향
    private fun checkUpperLeftCombo(row: Int, col: Int) : Int {
        var combo = 1
        val targetStone = getBoardImageView(row, col)

        for (d in 1..<scoreCombo) {
            if (col + d >= colSize || row + d >= rowSize ||
                targetStone.drawable != getBoardImageView(row + d, col + d).drawable)
                break
            combo++
        }
        for (d in 1..<scoreCombo) {
            if (col - d < 0 || row - d < 0 ||
                targetStone.drawable != getBoardImageView(row - d, col - d).drawable)
                break
            combo++
        }

        return combo
    }

    // 현재 combo가 게임이 끝나는 기준을 넘었는지 확인
    private fun isGameEnd(combo: Int): Boolean {
        return combo >= scoreCombo
    }

    // 게임 종료 알림 함수
    private fun alertEnd(row: Int, col: Int): Unit {
        val targetStone = getBoardImageView(row, col)
        AlertDialog.Builder(context).apply {
            setCancelable(false)
            setTitle("게임이 종료되었습니다! 😎")
            if (targetStone.drawable == cache.stoneCache[cache.STONE_BLACK])
                setMessage("🎉${BLACK}⚫️이 승리했습니다!🎉\n재시작 하시겠습니까?")
            else
                setMessage("🎉${WHITE}⚪️️이 승리했습니다!🎉\n재시작 하시겠습니까?")

            setPositiveButton("네") { dialog, _ ->
                resetBoard()
                dialog.dismiss()
            }

            setNegativeButton("아니요") { dialog, _ ->
                dialog.dismiss()
            }
        }.create().show()
    }

    /* 보드 초기화 함수 */
    private fun resetBoard(): Unit {
        board.children.forEach { tableRow ->
            tableRow as TableRow
            tableRow.children.forEach { imgView ->
                imgView as ImageView
                imgView.setImageDrawable(null)
            }
        }
        nowStone = BLACK
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