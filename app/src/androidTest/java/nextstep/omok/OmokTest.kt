package nextstep.omok

import androidx.test.core.app.ActivityScenario
import org.assertj.core.api.Assertions
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OmokTest {
    private lateinit var activity: MainActivity
    @BeforeEach
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java).onActivity {
            activity = it
        }
    }

    @Test
    // player 변경 test
    fun testSwitchPlayer() {
        activity.currentPlayer = "w"
        activity.switchPlayer()
        Assertions.assertThat(activity.currentPlayer).isEqualTo("b")
        activity.switchPlayer()
        Assertions.assertThat(activity.currentPlayer).isEqualTo("w")
    }

    // 가로, 대각선 방향 승리 test
    @Test
    fun testWinHorizontal() {
        for (i in 0..4) {
            activity.boardState[0][i] = "w"
        }
        assertTrue(activity.checkWin(0, 0))
    }
    @Test
    fun testWinDiagonal() {
        for (i in 0..4) {
            activity.boardState[i][i] = "w"
        }
        assertThat(activity.checkWin(0, 0)).isTrue()
    }

    @Test
    // countStones test
    fun testCountStones() {
        activity.boardState[0][0] = "w"
        activity.boardState[0][1] = "w"
        activity.boardState[1][0] = "w"

        assertEquals(1, activity.countStones(0, 0, 1, 0))
        assertEquals(1, activity.countStones(0, 0, 0, 1))
    }
}