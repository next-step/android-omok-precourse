package nextstep.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val blackPlayer = Player("black")
        val whitePlayer = Player("white")
        val omok = Game(blackPlayer, whitePlayer)

        val text = findViewById<TextView>(R.id.text)
        val board = findViewById<TableLayout>(R.id.board)
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view ->
                view.setOnClickListener {
                    val row = view.getParent() as TableRow
                    val indexRow: Int = board.indexOfChild(row)
                    val indexCol: Int = row.indexOfChild(view)

                    // 돌 두고 위치 기록하기
                    omok.currentPlayer.putStone(view)
                    omok.recordBoard(indexRow, indexCol)

                    // 오목인 경우 게임종료
                    if (omok.checkOmok(indexRow, indexCol, omok.currentPlayer.color)) {
                        omok.gameOver(text, omok.currentPlayer, board)
                    }

                    // 턴바꾸기
                    omok.changeTurn()
                }
            }
    }
}

class Game(val blackPlayer: Player, val whitePlayer: Player) {
    val board = Array(15, { Array(15, { "blank" }) })
    var currentPlayer: Player = blackPlayer // 시작은 흑돌 먼저

    // 턴바꾸기 - 흑돌인 경우 백돌로, 백돌인 경우 흑돌로
    fun changeTurn() {
        if (currentPlayer == blackPlayer) {
            currentPlayer = whitePlayer
        } else {
            currentPlayer = blackPlayer
        }
    }

    // 돌의 위치 기록하기 - 각 돌의 색깔을 2차원 배열에 저장
    fun recordBoard(row: Int, col: Int) {
        //Log.d("testt", "index : " + row + "," + col)
        board[row][col] = currentPlayer.color
    }

    // 오목인지 확인하는 함수 - 모든 체크 함수 호출하기
    fun checkOmok(row: Int, col: Int, stone: String): Boolean {
        if (checkLeftToRight(row, stone)) return true
        if (checkTopToBottom(col, stone)) return true
        if (checkTopLeftToBottomRight(row, col, stone)) return true
        if (checkTopRightToBottom(row, col, stone)) return true
        return false
    }

    // 1. 좌우 확인
    fun checkLeftToRight(row: Int, stone: String): Boolean {
        var cnt: Int = 0
        for (i in 0..14) {
            if (board[row][i] == stone) cnt++
            else cnt = 0
            if (cnt == 5) return true
        }
        return false
    }

    // 2. 상하 확인
    fun checkTopToBottom(col: Int, stone: String): Boolean {
        var cnt: Int = 0
        for (i in 0..14) {
            if (board[i][col] == stone) cnt++
            else cnt = 0
            if (cnt == 5) return true
        }
        return false
    }

    // 3. 좌상단에서 우하단 확인
    fun checkTopLeftToBottomRight(row: Int, col: Int, stone: String): Boolean {
        if (row > col) {
            if (checkTopLeftToBottomRightBigRow(row - col, stone)) {
                return true
            }
        } else {
            if (checkTopLeftToBottomRightBigCol(col - row, stone)) {
                return true
            }
        }
        return false
    }

    // 4. 우상단에서 좌하단 확인
    fun checkTopRightToBottom(row: Int, col: Int, stone: String): Boolean {
        if (row + col < 15) {
            if (checkTopRightToBottomLeftUnder15(row + col, stone)) {
                return true
            }
        } else {
            if (checkTopRightToBottomLeftOver15(row + col, stone)) {
                return true
            }
        }
        return false
    }

    // 3-1. 좌상단에서 우하단에서 Row가 더 큰경우 -> (n,0)부터 각각+1하며 탐색시작
    fun checkTopLeftToBottomRightBigRow(rowMinusCol: Int, stone: String): Boolean {
        var cnt: Int = 0
        for (i in 0..14) {
            if (rowMinusCol + i > 14) break
            if (board[rowMinusCol + i][0 + i] == stone) cnt++
            else cnt = 0
            if (cnt == 5) return true
        }
        return false
    }

    // 3-2. 좌상단에서 우하단에서 Col이 더 큰경우 -> (0,n)부터 각각+1하며 탐색시작
    fun checkTopLeftToBottomRightBigCol(colMinusRow: Int, stone: String): Boolean {
        var cnt: Int = 0
        for (i in 0..14) {
            if (colMinusRow + i > 14) break
            if (board[0 + i][colMinusRow + i] == stone) cnt++
            else cnt = 0
            if (cnt == 5) return true
        }
        return false
    }

    // 4-1. 우상단에서 좌하단에서 colPlusRow가 15이상 -> (n,0)부터 각각-1하며 탐색시작
    fun checkTopRightToBottomLeftUnder15(colPlusRow: Int, stone: String): Boolean {
        var cnt: Int = 0
        for (i in 0..14) {
            if (colPlusRow - i < 0) break
            if (board[colPlusRow - i][0 + i] == stone) cnt++
            else cnt = 0
            if (cnt == 5) return true
        }
        return false
    }

    // 4-1. 우상단에서 좌하단에서 colPlusRow가 15초과 -> (14,n)부터 각각-1하며 탐색시작
    fun checkTopRightToBottomLeftOver15(colPlusRow: Int, stone: String): Boolean {
        var cnt: Int = 0
        for (i in 0..14) {
            if (colPlusRow - 14 + i > 14) break
            if (board[14 - i][colPlusRow - 14 + i] == stone) cnt++
            else cnt = 0
            if (cnt == 5) return true
        }
        return false
    }

    // 게임 종료 - 텍스트에 종료 표시(이긴돌 색 표시), 더이상 클릭 안되도록
    fun gameOver(view: TextView, currentPlayer: Player, tableLayout: TableLayout) {
        view.text = "게임 종료 " + currentPlayer.color + " 승리"
        tableLayout
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { view -> view.isClickable = false } // 클릭끄기
    }
}

class Player(
    val color: String
) {
    // 돌두기 - 이미 돌을 둔 칸은 못두도록
    fun putStone(view: ImageView) {
        if (color == "black") {
            view.setImageResource(R.drawable.black_stone)
        } else {
            view.setImageResource(R.drawable.white_stone)
        }
        view.isClickable = false // 클릭끄기
    }
}
