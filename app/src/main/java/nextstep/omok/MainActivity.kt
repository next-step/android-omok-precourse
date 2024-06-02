package nextstep.omok

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

const val BOARD_SIZE: Int = 15

class MainActivity : AppCompatActivity() {
    private var turn: User = User.BLACK
    private var boards = Array(BOARD_SIZE) { Array<BoardState>(BOARD_SIZE) { BoardState.EMPTY } }
    private fun changeTurn() {
        this.turn = if (turn == User.BLACK) User.WHITE else User.BLACK
    }

    // 해당 인덱스가 빈 공간인지 확인한다.
    private fun isEmptySpace(idx: Int): Boolean {
        return boards[idx / BOARD_SIZE][idx % BOARD_SIZE] == BoardState.EMPTY
    }

    // 각 방향에 승리 조건(연속된 5개 이상의 같은 돌)이 있는지 판별한다.
    private fun isWin(idx: Int): Boolean {
        val col = idx / BOARD_SIZE
        val row = idx % BOARD_SIZE

        return checkDirection(col, row, 1, 0) ||  // 수직
                checkDirection(col, row, 0, 1) ||  // 수평
                checkDirection(col, row, 1, 1) ||  // 대각선 /
                checkDirection(col, row, 1, -1)    // 대각선 \
    }

    // 각 방향(수평, 수직, 대각선)에 해당하는 두가지 방향(수평일 시 좌, 우)에 5개 이상의 같은 돌이 있는지 확인한다.
    // col, row는 착수한 돌의 행, 열 인덱스이며 colStep, rowStep은 각 행,열로의 이동값이다.
    // target은 착수한 돌의 boardState이며 count는 연속된 같은 돌의 개수이다.
    private fun checkDirection(col: Int, row: Int, colStep: Int, rowStep: Int): Boolean {
        val target = boards[col][row]
        var count = 1

        count += countDirection(col, row, colStep, rowStep, target)
        count += countDirection(col, row, -colStep, -rowStep, target)

        return count >= 5
    }

    //boards를 방문하여 target(착수한 돌)과 연속된 같은 돌의 개수를 반환한다
    private fun countDirection(
        col: Int,
        row: Int,
        colStep: Int,
        rowStep: Int,
        target: BoardState
    ): Int {
        var count = 0
        var currentCol = col + colStep
        var currentRow = row + rowStep

        while (true) {
            if (currentCol < 0 || currentCol >= BOARD_SIZE) break
            if (currentRow < 0 || currentRow >= BOARD_SIZE) break
            if (boards[currentCol][currentRow] != target) break
            count++
            currentCol += colStep
            currentRow += rowStep
        }

        return count
    }

    private fun placeStoneView(view: ImageView) {
        val stone = if (turn == User.BLACK) R.drawable.black_stone else R.drawable.white_stone
        view.setImageResource(stone)
    }

    private fun placeStone(idx: Int, view: ImageView) {
        val col = idx / BOARD_SIZE
        val row = idx % BOARD_SIZE

        boards[col][row] = if (turn == User.BLACK) BoardState.BLACK else BoardState.WHITE
        placeStoneView(view)

    }


    private fun initListensers(board: TableLayout) {
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { idx, view ->
                view.setOnClickListener {
                    if (isEmptySpace(idx)) {
                        placeStone(idx, view)
                        changeTurn()
                    }
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)

        initListensers(board)
    }
}
