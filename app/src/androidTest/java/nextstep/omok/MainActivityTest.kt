package nextstep.omok

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun testPlaceStone() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                // 보드의 (1, 1) 위치를 클릭하여 돌을 둠
                val board = activity.findViewById<TableLayout>(R.id.board)
                val row = board.getChildAt(1) as TableRow
                val cell = row.getChildAt(1) as ImageView
                cell.performClick()

                // 클릭한 위치에 올바른 돌이 놓였는지 확인
                val drawable = cell.drawable
                assert(drawable != null) { "Drawable should not be null" }
            }
        }
    }

    @Test
    fun testShowGameOverDialog() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity -> placeStonesToWin(activity) } // Dialog가 화면에 표시되는지 확인
            onView(withText("게임 종료")).inRoot(isDialog()).check(matches(isDisplayed())) // 게임 종료 대화상자 내의 승리 메시지가 정확하게 표시되는지 확인
            onView(withText("흑이 승리했습니다. 새 게임을 진행하시겠습니까?")).inRoot(isDialog()).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testRestartGame() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity -> placeStonesToWin(activity) }
            onView(withText("게임 종료")).inRoot(isDialog()).check(matches(isDisplayed())) // Dialog가 화면에 표시되는지 확인
            onView(withText("Yes")).perform(click()) // "Yes" 버튼을 눌러 새로운 게임 시작
            onView(withId(R.id.board)).check { view, _ ->
                val board = view as TableLayout
                for (rowIndex in 0 until board.childCount) {
                    val row = board.getChildAt(rowIndex) as TableRow
                    for (columnIndex in 0 until row.childCount) {
                        val cell = row.getChildAt(columnIndex) as ImageView
                        assert(cell.drawable == null) { "Board should be reset, but found a stone at ($rowIndex, $columnIndex)" }
                    }
                }
            }// 새로운 게임이 시작되었는지 확인 (보드가 초기화되었는지 확인)
        }
    }

    @Test
    fun testEndGame() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity { activity -> placeStonesToWin(activity) }
            onView(withText("게임 종료")).inRoot(isDialog()).check(matches(isDisplayed())) // Dialog가 화면에 표시되는지 확인
            onView(withText("No")).perform(click()) // "No" 버튼을 눌러 앱 종료
            scenario.onActivity { activity -> assert(activity.isFinishing || activity.isDestroyed) { "Activity should be finishing or destroyed" } }// 액티비티가 종료되었는지 확인
        }
    }

    private fun placeStonesToWin(activity: MainActivity) {
        for (i in 0..4) {
            val cellBlack = getImageViewAt(activity, 0, i)
            activity.placeStone(cellBlack, 0, i)
            if (i < 4) {
                val cellWhite = getImageViewAt(activity, 1, i)
                activity.placeStone(cellWhite, 1, i)
            }
        }
    } // 흑이 승리하는 시나리오

    private fun getImageViewAt(activity: MainActivity, row: Int, col: Int): ImageView {
        val board = activity.findViewById<TableLayout>(R.id.board)
        val tableRow = board.getChildAt(row) as TableRow
        return tableRow.getChildAt(col) as ImageView
    }
}
