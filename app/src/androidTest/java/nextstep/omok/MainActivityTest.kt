package nextstep.omok

import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainActivityTest {

    private lateinit var mainActivity: MainActivity

    @BeforeEach
    fun setUp() {
        mainActivity = MainActivity()
    }

    @Test
    fun shouldPlaceStoneAndSwitchPlayer() {
        val imageView = ImageView(mainActivity)
        mainActivity.placeStone(imageView)

        assertThat(mainActivity.currentPlayer).isEqualTo("백돌")
    }

    @Test
    fun shouldCheckWinInHorizontalDirection() {
        val board = TableLayout(mainActivity)
        val row = 0
        val col = 0
        val rowDelta = 1
        val colDelta = 0

        assertThat(mainActivity.checkDirection(ImageView(mainActivity), rowDelta, colDelta)).isFalse
    }

    @Test
    fun ShouldCheckWinInVerticalDirection() {
        val board = TableLayout(mainActivity)
        val row = 0
        val col = 0
        val rowDelta = 0
        val colDelta = 1

        assertThat(mainActivity.checkDirection(ImageView(mainActivity), rowDelta, colDelta)).isFalse
    }

    @Test
    fun shouldCheckWinInDiagonalDirection() {
        val board = TableLayout(mainActivity)
        val row = 0
        val col = 0
        val rowDelta = 1
        val colDelta = 1

        assertThat(mainActivity.checkDirection(ImageView(mainActivity), rowDelta, colDelta)).isFalse
    }

    @Test
    fun shouldClearBoard() {
        val board = TableLayout(mainActivity)
        val tableRow = TableRow(mainActivity)
        val imageView = ImageView(mainActivity)

        board.addView(tableRow)
        tableRow.addView(imageView)

        mainActivity.clearBoard()

        assertThat(imageView.drawable).isNull()
        assertThat(imageView.tag).isNull()
    }
}
