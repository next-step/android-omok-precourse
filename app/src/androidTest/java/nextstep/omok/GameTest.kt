package nextstep.omok

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameTest {
    private lateinit var game: Game
    @Before
    fun setUp(){
        game = Game(15, 15, true)
    }
}