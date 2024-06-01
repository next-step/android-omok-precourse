package nextstep.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class OmokBoardTest {
	@ParameterizedTest
	@CsvSource(
		"0-0-1/0-1-1/0-2-1/0-3-1/0-4-1, 0, 0, true",
		"0-0-1/0-1-1/0-2-1/0-3-1/0-4-1, 1, 0, false",
		"0-0-1/0-1-1/0-2-1/0-3-1/0-4-1/0-5-1, 0, 0, true",
		"0-0-1/0-1-1/0-2-1/0-3-1/0-4-2/0-5-1, 0, 1, false",
		"0-0-1/0-1-1/0-2-1/0-3-1/1-4-1/0-5-1, 0, 1, false",
		"0-0-2/0-1-2/0-2-2/0-3-2/0-4-2/0-5-2, 0, 0, true",

		"0-0-1/1-0-1/2-0-1/3-0-1/4-0-1, 0, 0, true",
		"0-0-1/1-0-1/2-0-1/3-0-1/4-0-1, 0, 1, false",
		"0-0-1/1-0-1/2-0-1/3-0-1/4-0-1/5-0-1/6-0-1, 0, 0, true",
		"0-0-1/1-0-1/2-0-1/3-0-1/4-1-1/5-0-1/6-0-1, 0, 0, false",
		"0-0-1/1-0-1/2-0-1/3-0-2/4-0-1/5-0-1/6-0-1, 0, 0, false",

		"0-0-1/1-1-1/2-2-1/3-3-1/4-4-1, 0, 0, true",
		"0-0-1/1-1-1/2-2-1/3-3-1/4-4-1/5-5-1/6-6-1, 0, 0, true",
		"0-0-1/1-1-1/2-2-1/3-3-1/4-4-1, 2, 2, true",
		"0-0-1/1-1-1/2-2-1/3-3-2/4-4-1, 2, 2, false",
		"0-0-1/1-1-1/2-2-1/3-3-1/5-5-1, 3, 3, false",

		"2-7-2/3-6-2/4-5-2/5-4-2/6-3-2, 4, 5, true",
		"2-7-2/3-6-2/4-5-2/5-4-2/6-3-2, 4, 6, false",
		"2-7-2/3-6-2/4-5-2/5-4-2/6-3-2/7-2-2/8-1-2, 5, 4, true",
		"2-7-2/3-6-2/4-5-2/5-4-2/6-3-1/7-2-2/8-1-2, 5, 4, false",
		"2-7-2/3-6-2/4-5-2/5-4-2/7-2-2/8-1-2, 5, 4, false",
	)
	fun testCheckCurStoneIsWinner(inputBoard: String, tarR: Int, tarC: Int, expected: Boolean) {
		val curBoard = OmokBoard()
		inputBoard.split("/").forEach {
			val (r, c, player) = it.split("-")
			curBoard.putStone(r.toInt(), c.toInt(), if (player == "1") Player.BLACK else Player.WHITE)
		}

		assertThat(curBoard.checkCurStoneIsWinner(tarR, tarC)).isEqualTo(expected)
	}
}