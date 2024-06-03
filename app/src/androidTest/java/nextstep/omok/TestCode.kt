
package nextstep.omok

import android.view.WindowManager
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Root

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    // 돌 놓기 테스트
    @Test
    fun testPlaceStone() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val board = activity.findViewById<TableLayout>(R.id.board)
                val cell = (board.getChildAt(7) as TableRow).getChildAt(7) as ImageView

                activity.runOnUiThread {
                    activity.onCellClick(cell, 7, 7)
                }

                val drawable = cell.drawable

                assertThat(drawable).isNotNull
                assertThat(activity.boardState[7][7]).isEqualTo(1)
            }
        }
    }

    // 플레이어 순서 변경 테스트
    @Test
    fun testSwitchPlayer() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                activity.isBlackTurn = true

                val cell1 = ImageView(activity)
                activity.runOnUiThread {
                    activity.onCellClick(cell1, 0, 0)
                }
                assertThat(activity.isBlackTurn).isFalse

                val cell2 = ImageView(activity)
                activity.runOnUiThread {
                    activity.onCellClick(cell2, 0, 1)
                }
                assertThat(activity.isBlackTurn).isTrue
            }
        }
    }

    // 보드 초기화 테스트
    @Test
    fun testBoardReset() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                val board = activity.findViewById<TableLayout>(R.id.board)
                activity.runOnUiThread {
                    activity.onCellClick((board.getChildAt(7) as TableRow).getChildAt(7) as ImageView, 7, 7)
                    activity.onCellClick((board.getChildAt(8) as TableRow).getChildAt(8) as ImageView, 8, 8)
                }

                activity.runOnUiThread {
                    activity.resetBoard(board)
                }

                for (i in 0 until board.childCount) {
                    val row = board.getChildAt(i) as TableRow
                    for (j in 0 until row.childCount) {
                        val cell = row.getChildAt(j) as ImageView
                        assertThat(cell.drawable).isNull()
                    }
                }

                for (row in activity.boardState) {
                    for (cell in row) {
                        assertThat(cell).isEqualTo(0)
                    }
                }
                assertThat(activity.isBlackTurn).isTrue
                assertThat(activity.gameEnded).isFalse
            }
        }
    }
}

// 토스트 메시지 확인
class ToastMatcher : TypeSafeMatcher<Root?>() {
    override fun describeTo(description: Description) {
        description.appendText("is toast")
    }

    public override fun matchesSafely(root: Root?): Boolean {
        val type = root?.windowLayoutParams?.get()?.type
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken = root.decorView.windowToken
            val appToken = root.decorView.applicationWindowToken
            return windowToken === appToken
        }
        return false
    }
}
