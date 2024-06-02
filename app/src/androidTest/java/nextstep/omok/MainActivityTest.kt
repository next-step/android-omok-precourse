package nextstep.omok

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

	private lateinit var activity: MainActivity

	@Test
	fun test() {
		assertThat(true).isTrue
	}

	@BeforeEach
	fun setUp() {
		ActivityScenario.launch(MainActivity::class.java).onActivity {
			activity = it
		}
	}

	@Test
	fun testStonePlacement() {
		val imageView = activity.board[0][0]
		imageView?.tag = "0,0"

		activity.runOnUiThread {
			activity.onStonePlaced(imageView!!)
			Log.d("testStonePlacement", "Tag after placement: ${imageView?.tag}")
			Log.d("testStonePlacement", "Drawable after placement: ${imageView?.drawable}")

			assertThat(imageView?.drawable).isNotNull
			assertThat(imageView?.tag).isEqualTo("흑돌")
		}
	}

	@Test
	fun testWinCondition() {
		// 승리 조건을 충족시키기 위해 돌을 놓음 (세로로 흑돌 5개)
		for (i in 0..4) {
			val imageView = activity.board[i][0]
			imageView?.tag = "$i,0"
			activity.runOnUiThread {
				activity.onStonePlaced(imageView!!)
				Log.d("testWinCondition", "Tag after placement: ${imageView?.tag}")
			}
			if (i < 4) {
				val opponentImageView = activity.board[i + 1][1]
				activity.runOnUiThread {
					activity.onStonePlaced(opponentImageView!!)
					Log.d("testWinCondition", "opponentImageView: ${opponentImageView?.tag}")
				}
			}
		}

		activity.runOnUiThread {
			// 승리 메시지가 표시되는지 확인
			assertThat(activity.checkWin(4, 0)).isTrue
		}
	}

	@Test
	fun testBoardReset() {
		// 돌을 놓아서 게임을 진행
		activity.runOnUiThread {
			activity.onStonePlaced(activity.board[0][0]!!)
			activity.onStonePlaced(activity.board[1][0]!!)
		}

		// 보드 초기화
		activity.runOnUiThread { activity.resetBoard() }

		// 보드가 초기화되었는지 확인
		for (i in 0 until 15) {
			for (j in 0 until 15) {
				val imageView = activity.board[i][j]
				assertThat(imageView?.drawable).isNull()
				assertThat(imageView?.tag).isEqualTo("$i,$j")
			}
		}
	}
}
