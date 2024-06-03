package nextstep.omok

class Board {
    val MAX_SIZE = 15
    private val board = Array(MAX_SIZE) { Array<Int>(MAX_SIZE) { 0 } }

    fun initBoard(){
        for (i in 0..< MAX_SIZE)
            for (j in 0..<MAX_SIZE)
                board[i][j] = 0
    }
    fun checkIndex(row: Int, col:Int):Boolean{
        return if(row < 0 || row >= MAX_SIZE || col < 0 || col >= MAX_SIZE){
            print("nullError" + "인덱스 오류")
            false
        } else true
    }

    fun isEmpty(row: Int, col: Int): Boolean? {
        return if (checkIndex(row, col)) board[row][col] == 0
        else null
    }

    fun checkStone(row : Int, col : Int) : Int?{
        return if (checkIndex(row, col)) board[row][col]
        else null
    }


}