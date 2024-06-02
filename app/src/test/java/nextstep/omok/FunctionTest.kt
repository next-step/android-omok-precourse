package nextstep.omok

import androidx.test.core.app.ActivityScenario
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.jupiter.api.Test

class FunctionTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testPutStoneInTurn() {
        with(scenario) {
            onActivity { activity ->
                activity.isBlackTurn = true
                activity.putStoneInTurn(1, 1)
                assertTrue(activity.board[1][1]?.tag as Boolean)
                assertFalse(activity.isBlackTurn)
            }
        }
    }

    @Test
    fun testCheckFiveLine() {
        with(scenario) {
            onActivity { activity ->
                activity.board[0][0]?.setImageResource(R.drawable.black_stone)
                activity.board[0][0]?.tag = true
                activity.board[0][1]?.setImageResource(R.drawable.black_stone)
                activity.board[0][1]?.tag = true
                // 나머지 위치에도 돌을 놓음
                assertTrue(activity.checkFiveLine(0, 2))
            }
        }
    }

    @Test
    fun testCheckHorizontal() {
        with(scenario) {
            onActivity { activity ->
                for (i in 0..4) {
                    activity.board[0][i]?.setImageResource(R.drawable.black_stone)
                    activity.board[0][i]?.tag = true
                }
                assertTrue(activity.checkHorizontal(0, true))
            }
        }
    }

    @Test
    fun testCheckVertical() {
        with(scenario) {
            onActivity { activity ->
                for (i in 0..4) {
                    activity.board[i][0]?.setImageResource(R.drawable.black_stone)
                    activity.board[i][0]?.tag = true
                }
                assertTrue(activity.checkVertical(0, true))
            }
        }
    }

    @Test
    fun testCheckDiagonal() {
        with(scenario) {
            onActivity { activity ->
                for (i in 0..4) {
                    activity.board[i][i]?.setImageResource(R.drawable.black_stone)
                    activity.board[i][i]?.tag = true
                }
                assertTrue(activity.checkDiagonal(2, 2, true))
            }
        }
    }

    @Test
    fun testCheckReverseDiagonal() {
        with(scenario) {
            onActivity { activity ->
                for (i in 0..4) {
                    activity.board[i][4 - i]?.setImageResource(R.drawable.black_stone)
                    activity.board[i][4 - i]?.tag = true
                }
                assertTrue(activity.checkReverseDiagonal(2, 2, true))
            }
        }
    }
}