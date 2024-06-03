package nextstep.omok

import org.assertj.core.api.Assertions.*
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.test.core.app.ActivityScenario
import org.junit.Test

class OmokSystemAndroidTest {


    @Test
    fun getBoardImageViewTest() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity {
            val board = it.findViewById<TableLayout>(R.id.board)
            val omokSystem = OmokSystem(it, board)
            val imageView: ImageView = (board.getChildAt(1) as TableRow).getChildAt(1) as ImageView
            assertThat(omokSystem.getBoardImageView(1,1)).isEqualTo(imageView)
        }
        activityScenario.close()
    }

    @Test
    fun putStoneTest() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity {
            val board = it.findViewById<TableLayout>(R.id.board)
            val omokSystem = OmokSystem(it, board)

            // 지정한 위치에 착수
            setStoneBlack(omokSystem)
            omokSystem.putStone(1, 1)
            val oldStone = (board.getChildAt(1) as TableRow).getChildAt(1) as ImageView
            assertThat(omokSystem.getBoardImageView(1,1)).isEqualTo(oldStone)

            // 이미 착수한 곳에 착수 시도: 기존의 돌이 유지되어야 함
            omokSystem.putStone(1, 1)
            assertThat(omokSystem.getBoardImageView(1, 1)).isEqualTo(oldStone)
        }
        activityScenario.close()
    }

    @Test
    fun calculateCombo() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        activityScenario.onActivity {
            val board = it.findViewById<TableLayout>(R.id.board)
            val omokSystem = OmokSystem(it, board)

            setStoneBlack(omokSystem)
            omokSystem.putStone(1, 1)
            setStoneBlack(omokSystem)
            omokSystem.putStone(1, 2)
            setStoneBlack(omokSystem)
            omokSystem.putStone(1, 3)
            setStoneBlack(omokSystem)
            omokSystem.putStone(1, 4)
            setStoneBlack(omokSystem)
            omokSystem.putStone(1, 5)
            assertThat(omokSystem.calculateCombo(1,1)).isEqualTo(5)
            assertThat(omokSystem.calculateCombo(1,2)).isEqualTo(5)
            assertThat(omokSystem.calculateCombo(1,3)).isEqualTo(5)
            assertThat(omokSystem.calculateCombo(1,4)).isEqualTo(5)
            assertThat(omokSystem.calculateCombo(1,5)).isEqualTo(5)

            setStoneWhite(omokSystem)
            omokSystem.putStone(2, 2)
            setStoneWhite(omokSystem)
            omokSystem.putStone(3, 3)
            setStoneWhite(omokSystem)
            omokSystem.putStone(4, 4)
            setStoneWhite(omokSystem)
            omokSystem.putStone(5, 5)
            setStoneWhite(omokSystem)
            omokSystem.putStone(6, 6)
            assertThat(omokSystem.calculateCombo(2,2)).isEqualTo(5)
            assertThat(omokSystem.calculateCombo(3,3)).isEqualTo(5)
            assertThat(omokSystem.calculateCombo(4,4)).isEqualTo(5)
            assertThat(omokSystem.calculateCombo(5,5)).isEqualTo(5)
            assertThat(omokSystem.calculateCombo(6,6)).isEqualTo(5)
        }
        activityScenario.close()
    }

    @Test
    fun isGameEndTest() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        activityScenario.onActivity {
            val board = it.findViewById<TableLayout>(R.id.board)
            val omokSystem = OmokSystem(it, board)

            assertThat(omokSystem.isGameEnd(omokSystem.scoreCombo - 1)).isEqualTo(false)
            assertThat(omokSystem.isGameEnd(omokSystem.scoreCombo)).isEqualTo(true)
            assertThat(omokSystem.isGameEnd(omokSystem.scoreCombo + 1)).isEqualTo(true)
        }
        activityScenario.close()

    }

    @Test
    fun resetBoardTest() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        activityScenario.onActivity {
            val board = it.findViewById<TableLayout>(R.id.board)
            val omokSystem = OmokSystem(it, board)

            // 판에 돌을 모두 착수
            for (i in 0..14) {
                for (j in 0..14) {
                    omokSystem.putStone(i,j)
                    assertThat(omokSystem.getBoardImageView(i,j).drawable).isNotNull
                }
            }
            omokSystem.resetBoard()
            // 리셋되었는지 확인
            for (i in 0..14) {
                for (j in 0..14) {
                    assertThat(omokSystem.getBoardImageView(i,j).drawable).isNull()
                }
            }
            assertThat(omokSystem.nowStone).isEqualTo(omokSystem.BLACK)
        }
        activityScenario.close()
    }

    fun setStoneBlack(omokSystem: OmokSystem) {
        omokSystem.nowStone = omokSystem.BLACK
    }

    fun setStoneWhite(omokSystem: OmokSystem) {
        omokSystem.nowStone = omokSystem.WHITE
    }
}