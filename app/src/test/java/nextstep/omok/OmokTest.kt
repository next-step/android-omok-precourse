package nextstep.omok

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class OmokTest {
    val blackPlayer = Player("black")
    val whitePlayer = Player("white")

    @Test
    fun testCheckLeftToRight(){
        val omok : Game = Game(blackPlayer, whitePlayer)
        omok.recordBoard(2, 0)
        omok.recordBoard(2, 1)
        omok.recordBoard(2, 2)
        omok.recordBoard(2, 3)
        omok.recordBoard(2, 4)
        val result = omok.checkLeftToRight(2, "black")
        assertEquals(true, result, "error in checkLeftToRight")
    }

    @Test
    fun testCheckTopToBottom() {
        val omok : Game = Game(blackPlayer, whitePlayer)
        omok.recordBoard(0, 2)
        omok.recordBoard(1, 2)
        omok.recordBoard(2, 2)
        omok.recordBoard(3, 2)
        omok.recordBoard(4, 2)
        val result = omok.checkTopToBottom(2, "black")
        assertEquals(true, result, "error in checkTopToBottom")
    }

    @Test
    fun testCheckTopLeftToBottomRightBigRow() {
        val omok : Game = Game(blackPlayer, whitePlayer)
        omok.recordBoard(2, 0)
        omok.recordBoard(3, 1)
        omok.recordBoard(4, 2)
        omok.recordBoard(5, 3)
        omok.recordBoard(6, 4)
        val result = omok.checkTopLeftToBottomRightBigRow(2, "black")
        assertEquals(true, result, "error in checkTopLeftToBottomRightBigRow")
    }

    @Test
    fun testCheckTopLeftToBottomRightBigCol() {
        val omok : Game = Game(blackPlayer, whitePlayer)
        omok.recordBoard(3, 4)
        omok.recordBoard(4, 5)
        omok.recordBoard(5, 6)
        omok.recordBoard(6, 7)
        omok.recordBoard(7, 8)
        val result = omok.checkTopLeftToBottomRightBigCol(1, "black")
        assertEquals(true, result, "error in checkTopLeftToBottomRightBigCol")
    }

    @Test
    fun testCheckTopRightToBottomLeftUnder15() {
        val omok : Game = Game(blackPlayer, whitePlayer)
        omok.recordBoard(0, 6)
        omok.recordBoard(1, 5)
        omok.recordBoard(2, 4)
        omok.recordBoard(3, 3)
        omok.recordBoard(4, 2)
        val result = omok.checkTopRightToBottomLeftUnder15(6, "black")
        assertEquals(true, result, "error in checkTopRightToBottomLeftUnder15")
    }

    @Test
    fun testCheckTopRightToBottomLeftOver15() {
        val omok : Game = Game(blackPlayer, whitePlayer)
        omok.recordBoard(10, 6)
        omok.recordBoard(9, 7)
        omok.recordBoard(8, 8)
        omok.recordBoard(7, 9)
        omok.recordBoard(6, 10)
        val result = omok.checkTopRightToBottomLeftOver15(16, "black")
        assertEquals(true, result, "error in checkTopRightToBottomLeftOver15")
    }
}