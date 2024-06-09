package nextstep.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
class Test {

    private lateinit var mainActivity: MainActivity

    @BeforeEach
    fun setUp() {
        mainActivity = MainActivity()
    }

    @Test
    fun `isEmptyPlace with empty place`() {
        val result = mainActivity.isEmptyPlace(0, 0)
        assertThat(result).isTrue()
    }

    @Test
    fun `isEmptyPlace with occupied place`() {
        mainActivity.stoneArray[0][0] = 1
        val result = mainActivity.isEmptyPlace(0, 0)
        assertThat(result).isFalse()
    }

//    @Test
//    fun `placeStone with black stone`() {
//        mainActivity.placeStone(ImageView(), 0, 0)
//        val result = mainActivity.stoneArray[0][0]
//        assertThat(result).isEqualTo(1)
//    }
//
//    @Test
//    fun `placeStone with white stone`() {
//        mainActivity.isblackturn = false
//        mainActivity.placeStone(ImageView(), 0, 0)
//        val result = mainActivity.stoneArray[0][0]
//        assertThat(result).isEqualTo(2)
//    }

    @Test
    fun `switchTurn from black to white`() {
        mainActivity.switchTurn()
        assertThat(mainActivity.isblackturn).isFalse()
    }

    @Test
    fun `switchTurn from white to black`() {
        mainActivity.isblackturn = false
        mainActivity.switchTurn()
        assertThat(mainActivity.isblackturn).isTrue()
    }

    @Test
    fun `checkFullBoard with full board`() {
        repeat(15) { row ->
            repeat(15) { col ->
                mainActivity.stoneArray[row][col] = 1
            }
        }
        mainActivity.checkFullBoard()
        assertThat(mainActivity.fullBoard).isEqualTo(0)
    }

    @Test
    fun `checkFullBoard with non-full board`() {
        mainActivity.checkFullBoard()
        assertThat(mainActivity.fullBoard).isEqualTo(225)
    }

}
