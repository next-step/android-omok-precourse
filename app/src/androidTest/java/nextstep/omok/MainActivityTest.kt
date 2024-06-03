package nextstep.omok

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var activity: MainActivity

    @BeforeEach
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java).onActivity {
            activity = it
        }
    }
    @Test
    // 흑돌 세로로 놓았을 때 승리조건 확인
    fun testCountStones() {
        activity.runOnUiThread {
            activity.stoneList[0][0] = 1
            activity.stoneList[0][1] = 1
            activity.stoneList[0][2] = 1
            activity.stoneList[0][3] = 1
            activity.stoneList[0][4] = 1


            assertEquals(true, activity.checkForWin(0, 2))
        }
    }
     //백돌을 대각선으로 놓았을때
    @Test
    fun testCountStonesDiagonal() {
        activity.runOnUiThread {
            activity.stoneList[0][0] = 2
            activity.stoneList[1][1] = 2
            activity.stoneList[2][2] = 2
            activity.stoneList[3][3] = 2
            activity.stoneList[4][4] = 2


            assertEquals(true, activity.checkForWin(0, 0))
        }
    }
}