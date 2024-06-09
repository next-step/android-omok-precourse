package nextstep.omok

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainActivityTest {

    lateinit var scenario: ActivityScenario<MainActivity>

    @BeforeEach
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testInitialState() {
        scenario.onActivity { activity ->
            val statusWindow = activity.findViewById<TextView>(R.id.StatusWindow)
            assertEquals("흑의 차례", statusWindow.text.toString())
            assertEquals(BLACK, activity.player)
            assertEquals(EMPTY, activity.winner)
            assertEquals(1, activity.turn)
            for (row in activity.boardState) {
                for (cell in row) {
                    assertEquals(EMPTY, cell)
                }
            }
        }
    }

    @Test
    fun testPlaceStone() {
        scenario.onActivity { activity ->
            val board = activity.findViewById<TableLayout>(R.id.board)
            val firstCell = board.getChildAt(1) as TableRow
            val firstImageView = firstCell.getChildAt(0) as ImageView

            assertEquals(EMPTY, activity.boardState[0][0])
            activity.game(0, firstImageView)
            assertEquals(BLACK, activity.boardState[0][0])
        }
    }

    @Test
    fun testSwitchTurn() {
        scenario.onActivity { activity ->
            val board = activity.findViewById<TableLayout>(R.id.board)
            val firstCell = board.getChildAt(1) as TableRow
            val firstImageView = firstCell.getChildAt(0) as ImageView

            assertEquals(BLACK, activity.player)

            activity.game(0, firstImageView)
            assertEquals(WHITE, activity.player)

            val secondImageView = firstCell.getChildAt(1) as ImageView
            activity.game(1, secondImageView)
            assertEquals(BLACK, activity.player)
        }
    }

    @Test
    fun testCheckWinner() {
        scenario.onActivity { activity ->
            val board = activity.findViewById<TableLayout>(R.id.board)
            val firstRow = board.getChildAt(1) as TableRow
            val secondRow = board.getChildAt(2) as TableRow
            for (i in 0 until WINCOUNT * 2) {
                if (i % 2 == 0) {
                    val cell = firstRow.getChildAt(i / 2) as ImageView
                    activity.game(i / 2, cell)
                } else {
                    val cell = secondRow.getChildAt(i / 2) as ImageView
                    activity.game(BOARDSIZE + i / 2, cell)
                }
            }
            assertEquals(BLACK, activity.winner)

            val statusWindow = activity.findViewById<TextView>(R.id.StatusWindow)
            assertEquals("흑의 승", statusWindow.text.toString())
        }
    }

    @Test
    fun testTieGame() {
        scenario.onActivity { activity ->
            activity.turn = BOARDSIZE * BOARDSIZE
            val board = activity.findViewById<TableLayout>(R.id.board)
            val lastCell = board.getChildAt(1) as TableRow
            val lastImageView = lastCell.getChildAt(0) as ImageView

            activity.game(0, lastImageView)
            assertEquals(TIE, activity.winner)

            val statusWindow = activity.findViewById<TextView>(R.id.StatusWindow)
            assertEquals("무승부", statusWindow.text.toString())
        }
    }
}
