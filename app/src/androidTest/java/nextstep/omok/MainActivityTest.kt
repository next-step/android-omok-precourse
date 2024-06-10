package nextstep.omok

import androidx.test.core.app.ActivityScenario
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.children
class MainActivityTest {
    private lateinit var activity: MainActivity

    @Test
    fun testStonePlacement() {
        ActivityScenario.launch(MainActivity::class.java).onActivity {
            activity = it

            val board = activity.findViewById<TableLayout>(R.id.board)
            val firstCell = board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .first()

            firstCell.performClick()

            val row = 0
            val col = 0
            val expectedDrawable = R.drawable.black_stone

            assertThat(firstCell.drawable).isNotNull
            assertThat((firstCell.drawable.constantState)).isEqualTo(activity.getDrawable(expectedDrawable)!!.constantState)
            assertThat(activity.boardState[row][col]).isEqualTo("black")
        }
    }

    @Test
    fun testFiveVictory() {
        ActivityScenario.launch(MainActivity::class.java).onActivity {
            activity = it

            val board = activity.findViewById<TableLayout>(R.id.board)
            val cells = board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .toList()

            for (i in 0 until 5) {
                val cell = cells[i]
                cell.performClick()
                if (i < 4) {
                    activity.changePlayer()
                }
            }

            val showWinner = activity.findViewById<TextView>(R.id.showWinner)

            assertThat(showWinner.text.toString()).isEqualTo("흑돌 승리!")
            assertThat(activity.winnerPlayer).isEqualTo("black")
            assertThat(activity.gameOver).isTrue
        }
    }
    @Test
    fun testFiveVictory2() {
        ActivityScenario.launch(MainActivity::class.java).onActivity {
            activity = it

            val board = activity.findViewById<TableLayout>(R.id.board)
            val cells = board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .toList()

            for (i in 0 until 5) {
                val cell = cells[i * (activity.boardSize + 1)]
                cell.performClick()
                if (i < 4) {
                    activity.changePlayer()
                }
            }

            val showWinner = activity.findViewById<TextView>(R.id.showWinner)

            assertThat(showWinner.text.toString()).isEqualTo("흑돌 승리!")
            assertThat(activity.winnerPlayer).isEqualTo("black")
            assertThat(activity.gameOver).isTrue
        }
    }
    @Test
    fun testFiveVictory3() {
        ActivityScenario.launch(MainActivity::class.java).onActivity {
            activity = it

            val board = activity.findViewById<TableLayout>(R.id.board)
            val cells = board
                .children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .toList()

            for (i in 0 until 5) {
                val cell = cells[(i + 1) * (activity.boardSize - 1)]
                cell.performClick()
                if (i < 4) {
                    activity.changePlayer()
                }
            }

            val showWinner = activity.findViewById<TextView>(R.id.showWinner)

            assertThat(showWinner.text.toString()).isEqualTo("흑돌 승리!")
            assertThat(activity.winnerPlayer).isEqualTo("black")
            assertThat(activity.gameOver).isTrue
        }
    }
}
