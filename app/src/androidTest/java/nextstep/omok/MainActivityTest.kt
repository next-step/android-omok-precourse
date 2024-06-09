package nextstep.omok

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @Test
    fun testPlaceStone(){
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                // 돌 위치 지정
                val row = 4
                val col = 6
                val imgWhite = activity.findViewById<ImageView>(R.id.img_white)
                val imgBlack = activity.findViewById<ImageView>(R.id.img_black)
                val board = activity.findViewById<TableLayout>(R.id.board)

                var target: ImageView? = null
                    board
                    .children
                    .filterIsInstance<TableRow>()
                    .flatMap { it.children }
                    .filterIsInstance<ImageView>()
                    .forEachIndexed { idx, view ->
                        var c_row = idx / activity.boardSize
                        var c_col = idx % activity.boardSize
                        if (c_row == row && c_col == col){
                            target = view
                        }
                    }


                activity.placeStone(target!!, row, col, imgWhite, imgBlack)

                assertEquals(Stone.BLACK, activity.checkedBoard[row][col])
            }
        }
    }

    @Test
    fun testInit(){
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val board = activity.findViewById<TableLayout>(R.id.board)

                activity.init(board) // 게임초기화

                for (row in activity.checkedBoard) {
                    for (stone in row) {
                        assertEquals(Stone.EMPTY, stone)
                    }
                }
            }
        }
    }

    @Test
    fun testCountStone(){
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                // 테스트 보드 설정
                activity.checkedBoard = MutableList(activity.boardSize) {
                    MutableList(activity.boardSize) { Stone.EMPTY }
                }
                activity.checkedBoard[0][0] = Stone.BLACK
                activity.checkedBoard[0][1] = Stone.BLACK
                activity.checkedBoard[0][2] = Stone.BLACK

                var count = activity.countStone(0, 0, 0, 1, Stone.BLACK, activity.checkedBoard)
                assertEquals(2, count)

                activity.checkedBoard[2][2] = Stone.BLACK
                activity.checkedBoard[3][3] = Stone.BLACK
                activity.checkedBoard[4][4] = Stone.BLACK

                count = activity.countStone(2, 2, 1, 1, Stone.BLACK, activity.checkedBoard)
                assertEquals(2, count)

            }
        }
    }
}
