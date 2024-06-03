package nextstep.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    internal val stoneList: MutableList<MutableList<Int>> = MutableList(15) {
        MutableList(15) { 0 }
    }
    internal var isBlackTurn = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setOnClickListener {
                    if (view.getTag() != null) {
                        return@setOnClickListener
                    }
                    var parentView = view.parent
                    while (parentView != null && parentView !is TableRow) {
                        parentView = parentView.parent
                    }
                    if (parentView !is TableRow) {
                        return@setOnClickListener
                    }
                    val x = (parentView).indexOfChild(view)
                    val y = board.indexOfChild(parentView)
                    if (isBlackTurn) {
                        view.setImageResource(R.drawable.black_stone)
                        view.setTag(R.drawable.black_stone)
                        stoneList[x][y] = 1
                    } else {
                        view.setImageResource(R.drawable.white_stone)
                        view.setTag(R.drawable.white_stone)
                        stoneList[x][y] = 2
                    }
//                    checkForWin(x, y)
                    isBlackTurn = !isBlackTurn
                }
            }
    }
    internal fun countStonesInDirection(dx: Int, dy: Int, x: Int, y: Int): Int {
        var count = 0
        var nx = x
        var ny = y
        val targetValue = if (isBlackTurn) 1 else 2
        while (nx in 0 until 15 && ny in 0 until 15 && stoneList[nx][ny] == targetValue) {
            count++
            nx += dx
            ny += dy
        }
        nx = x - dx
        ny = y - dy
        while (nx in 0 until 15 && ny in 0 until 15 && stoneList[nx][ny] == targetValue) {
            count++
            nx -= dx
            ny -= dy
        }
        return count
    }
    internal fun maxStonesInAllDirections(x: Int, y: Int): Int {
        var maxStones = 0
        val directions = arrayOf(
            Pair(0, 1),
            Pair(1, 0),
            Pair(1, -1),
            Pair(1, 1)
        )
        for ((dx, dy) in directions) {
            val directionCount = countStonesInDirection(dx, dy, x, y)
            maxStones = maxOf(maxStones, directionCount)
        }
        return maxStones
    }

}