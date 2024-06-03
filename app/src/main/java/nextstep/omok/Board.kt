package nextstep.omok

class Board {
    val MAX_SIZE = 15
    private val board = Array(MAX_SIZE) { Array<Int>(MAX_SIZE) { 0 } }

    fun initBoard(){
        for (i in 0..< MAX_SIZE)
            for (j in 0..<MAX_SIZE)
                board[i][j] = 0
    }
}