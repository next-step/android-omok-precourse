package nextstep.omok

import android.util.Log
import java.util.Stack

data class StonePlacement(val x: Int, val y: Int, val before: Int, val after: Int)

class Board(private val rows: Int, private val cols: Int) {
    private val stones: MutableList<MutableList<Int>> = mutableListOf()
    private val stonePlacementStackTrace: Stack<StonePlacement> = Stack<StonePlacement>()

    fun checkStonePlaceable(x: Int, y: Int):Boolean {
        if(y < 0 || y >= rows || x < 0 || x >= cols) {
            return false
        }

        return stones[y][x] == STONE_EMPTY
    }

    private fun checkStonePlacementFeasibility (x: Int, y: Int, stoneType: Int): Boolean {
        if(y < 0 || y >= rows || x < 0 || x >= cols) {
            Log.e("Board Error","the index of the stone is out of range.")
            return false
        }
        if(stoneType != STONE_EMPTY && stoneType != STONE_BLACK && stoneType != STONE_WHITE) {
            Log.e("Board Error","invalid type of stone.")
            return false
        }
        return true
    }

    private fun setStone(x: Int, y: Int, stoneType: Int):StonePlacement? {
        if(!checkStonePlacementFeasibility(x, y, stoneType))
            return null

        val result = StonePlacement(x, y, stones[y][x], stoneType)
        stonePlacementStackTrace.push(StonePlacement(x, y, stones[y][x], stoneType))
        stones[y][x] = stoneType
        return result
    }

    fun tryPlaceStone(x: Int, y:Int, stoneType: Int):StonePlacement? {
        if(!checkStonePlaceable(x, y))
            return null

        return setStone(x, y, stoneType)
    }

    fun getStone(x:Int, y:Int): Int = stones[y][x]

    fun getStonePlacementList():List<StonePlacement> = stonePlacementStackTrace.toList()

    init {
        initiateStones(rows, cols)
    }

    private fun initiateStones(rows: Int, cols: Int) {
        stones.clear()
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