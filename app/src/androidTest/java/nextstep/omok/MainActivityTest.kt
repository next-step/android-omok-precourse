package nextstep.omok

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    private fun <T> getPrivateField(instance: Any, fieldName: String): T {
        val field = instance.javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(instance) as T
    }

    private fun callPrivateMethod(instance: Any, methodName: String, vararg args: Any?): Any? {
        val parameterTypes = args.map {
            when (it) {
                is Int -> Int::class.javaPrimitiveType
                else -> it?.javaClass
            }
        }.toTypedArray()
        val method = instance.javaClass.getDeclaredMethod(methodName, *parameterTypes)
        method.isAccessible = true
        return method.invoke(instance, *args)
    }


    @Test
    fun testInitializeBoard() {
        scenario.onActivity { activity ->
            val board = activity.findViewById<TableLayout>(R.id.board)
            board.children.filterIsInstance<TableRow>().forEach { row ->
                row.children.filterIsInstance<ImageView>().forEach { view ->
                    assertThat(view.drawable).isNull()  // ImageView should be empty
                }
            }
        }
    }

    @Test
    fun testPlaceStone() {
        scenario.onActivity { activity ->
            val row = 0
            val col = 0
            val board = activity.findViewById<TableLayout>(R.id.board)
            val cell = (board.getChildAt(row) as TableRow).getChildAt(col) as ImageView

            activity.runOnUiThread {
                cell.performClick()
            }

            val boardArray = getPrivateField<Array<IntArray>>(activity, "BOARD_ARRAY")
            assertThat(boardArray[row][col]).isEqualTo(1)  // Black stone should be placed

            activity.runOnUiThread {
                cell.performClick()
            }

            assertThat(boardArray[row][col]).isEqualTo(1)  // Should not change on second click
        }
    }

    @Test
    fun testSwitchTurns() {
        scenario.onActivity { activity ->
            val row = 0
            val col = 0
            val nextRow = 0
            val nextCol = 1
            val board = activity.findViewById<TableLayout>(R.id.board)
            val cell1 = (board.getChildAt(row) as TableRow).getChildAt(col) as ImageView
            val cell2 = (board.getChildAt(nextRow) as TableRow).getChildAt(nextCol) as ImageView

            activity.runOnUiThread {
                cell1.performClick()
            }

            val boardArray = getPrivateField<Array<IntArray>>(activity, "BOARD_ARRAY")
            assertThat(boardArray[row][col]).isEqualTo(1)  // Black stone

            activity.runOnUiThread {
                cell2.performClick()
            }

            assertThat(boardArray[nextRow][nextCol]).isEqualTo(2)  // White stone
        }
    }

    @Test
    fun testCheckWin() {
        scenario.onActivity { activity ->
            val boardArray = getPrivateField<Array<IntArray>>(activity, "BOARD_ARRAY")
            boardArray[0][0] = 1
            boardArray[0][1] = 1
            boardArray[0][2] = 1
            boardArray[0][3] = 1
            boardArray[0][4] = 1

            val hasWon = callPrivateMethod(activity, "checkWin", 0, 4) as Boolean
            assertThat(hasWon).isTrue()  // Black wins
        }
    }

    @Test
    fun testResetBoard() {
        scenario.onActivity { activity ->
            val boardArray = getPrivateField<Array<IntArray>>(activity, "BOARD_ARRAY")
            boardArray[0][0] = 1

            callPrivateMethod(activity, "resetBoard")

            boardArray.forEach { row ->
                row.forEach { cell ->
                    assertThat(cell).isEqualTo(0)  // Board should be reset to empty
                }
            }

            val board = activity.findViewById<TableLayout>(R.id.board)
            board.children.filterIsInstance<TableRow>().forEach { row ->
                row.children.filterIsInstance<ImageView>().forEach { view ->
                    assertThat(view.drawable).isNull()  // ImageView should be empty
                }
            }
        }
    }
}
