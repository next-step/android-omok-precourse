package nextstep.omok

import android.util.Log

class Board(private val rows: Int, private val cols: Int) {
    private val stones: MutableList<MutableList<Int>> = mutableListOf()

    fun checkStonePlaceable(x: Int, y: Int):Boolean {
        if(y < 0 || y >= rows || x < 0 || x >= cols) {
            return false
        }

        return stones[y][x] == STONE_EMPTY
    }

    private fun setStone(x: Int, y: Int, stoneType: Int) {
        if(y < 0 || y >= rows || x < 0 || x >= cols) {
            Log.e("Board Error","the index of the stone is out of range.")
            return
        }
        if(stoneType != STONE_EMPTY && stoneType != STONE_BLACK && stoneType != STONE_WHITE) {
            Log.e("Board Error","invalid type of stone.")
            return
        }

        stones[y][x] = stoneType
    }

    init {
        initiateStones(rows, cols)
    }

    private fun initiateStones(rows: Int, cols: Int) {
        for(i in 0 until rows) {
            addEmptyStoneRow(cols)
        }
    }

    private fun addEmptyStoneRow(cols: Int) {
        stones.add(mutableListOf())
        for(i in 0 until cols) {
            stones.last().add(STONE_EMPTY)
        }
    }

    companion object{
        const val STONE_EMPTY = 0
        const val STONE_BLACK = 1
        const val STONE_WHITE = 2
    }
}