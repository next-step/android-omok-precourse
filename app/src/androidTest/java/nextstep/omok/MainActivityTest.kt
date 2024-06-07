package nextstep.omok

import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.view.children
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Description
import org.hamcrest.Matcher
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import junit.framework.TestCase.assertTrue

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testPlaceStoneButton() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            // 보드의 (7, 7) 위치를 클릭하여 돌을 둠
            onView(findCellInBoard(withId(R.id.board), 7, 7)).perform(click())
            onView(withId(R.id.place_stone_btn)).perform(click())

            // 보드에서 (7, 7) 위치의 imageView를 찾아 이미지가 생겼는지 확인
            scenario.onActivity { activity ->
                val board = activity.findViewById<TableLayout>(R.id.board)
                val cell = (board.getChildAt(7) as TableRow).getChildAt(7) as ImageView
                val drawable = cell.drawable

                assertThat(drawable).isNotNull
            }
        }
    }

    @Test
    fun testRestartButton() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            // 임의의 위치 (7, 7), (8, 9)에 돌을 둠
            onView(findCellInBoard(withId(R.id.board), 7, 7)).perform(click())
            onView(withId(R.id.place_stone_btn)).perform(click())
            onView(findCellInBoard(withId(R.id.board), 8, 9)).perform(click())
            onView(withId(R.id.place_stone_btn)).perform(click())

            // 다시 시작 버튼 클릭
            onView(withId(R.id.restart_btn)).perform(click())

            // board의 모든 칸의 이미지가 초기화 됐는지 체크
            scenario.onActivity { activity ->
                val board = activity.findViewById<TableLayout>(R.id.board)
                board.children
                    .filterIsInstance<TableRow>()
                    .flatMap { it.children }
                    .filterIsInstance<ImageView>()
                    .forEach { cell -> assertTrue(cell.drawable == null) }

            }
        }
    }

    @Test
    fun testShowGameOverDialog() {
        ActivityScenario.launch(MainActivity::class.java).use {
            // 0번째 줄에 5개의 돌을 놓아 오목을 완성시킴
            for (i in 1..4) {
                onView(findCellInBoard(withId(R.id.board), 0, i)).perform(click())
                onView(withId(R.id.place_stone_btn)).perform(click())

                onView(findCellInBoard(withId(R.id.board), 1, i)).perform(click())
                onView(withId(R.id.place_stone_btn)).perform(click())
            }
            onView(findCellInBoard(withId(R.id.board), 0, 5)).perform(click())
            onView(withId(R.id.place_stone_btn)).perform(click())


            // 게임 종료 대화상자가 화면에 표시되는지 확인(게임 종료 다시시작 버튼이 표시되는지 확인하는 방법)
            onView(withId(R.id.game_over_restart_btn)).check(matches(isDisplayed()))

            // 게임 종료 대화상자 내의 "흑돌 승리!!" 텍스트가 있는지 확인
            onView(withText("흑돌 승리!!")).inRoot(isDialog()).check(matches(isDisplayed()))

        }
    }

    private fun findCellInBoard(parentMatcher: Matcher<View>, rowIndex: Int, colIndex: Int): Matcher<View> {
        return object : BoundedMatcher<View, View>(View::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("$rowIndex 행, $colIndex 열")
                parentMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent

                if (parent is TableRow && parent.parent is TableLayout) {
                    val tableLayout = parent.parent as TableLayout
                    return tableLayout.indexOfChild(parent) == rowIndex && parent.indexOfChild(
                        view
                    ) == colIndex
                }
                return false
            }
        }
    }

}