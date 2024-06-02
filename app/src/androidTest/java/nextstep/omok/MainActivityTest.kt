package nextstep.omok

import androidx.test.core.app.ActivityScenario
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
class MainActivityTest {

	private lateinit var activity: MainActivity

	@Test
	@org.junit.jupiter.api.DisplayName("흑돌이 놓아지는지 확인")
	fun testStonePlacement() {
		ActivityScenario.launch(MainActivity::class.java).onActivity {
			activity = it
			val imageView = activity.board[0][0]

			activity.runOnUiThread {
				activity.onStonePlaced(imageView!!)

				assertThat(imageView?.drawable).isNotNull 	// 돌이 놓여졌는지 확인
				assertThat(imageView?.tag).isEqualTo("흑돌") // 흑돌이 놓여졌는지 확인
			}
		}

	}

	@Test
	@org.junit.jupiter.api.DisplayName("승리 조건을 충족시키기 위해 돌을 놓음")
	fun testWinCondition() {
		ActivityScenario.launch(MainActivity::class.java).onActivity {
			activity = it
			// 승리 조건을 충족시키기 위해 돌을 놓음 (세로로 흑돌 5개)
			for (i in 0..4) {
				val imageView = activity.board[i][0]
				imageView?.tag = "$i,0"
				activity.runOnUiThread {
					activity.onStonePlaced(imageView!!)
				}
				if (i < 4) {
					val opponentImageView = activity.board[i + 1][1]
					activity.runOnUiThread {
						activity.onStonePlaced(opponentImageView!!)
					}
				}
			}

			activity.runOnUiThread {
				// 승리 메시지가 표시되는지 확인
				assertThat(activity.checkWin(4, 0)).isTrue
			}
		}

	}

	@Test
	@org.junit.jupiter.api.DisplayName("보드 초기화")
	fun testBoardReset() {
		ActivityScenario.launch(MainActivity::class.java).onActivity {
			activity = it
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
}
