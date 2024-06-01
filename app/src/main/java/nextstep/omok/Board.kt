package nextstep.omok

import java.util.Stack
import kotlin.math.max

data class StonePlacement(val x: Int, val y: Int, val before: Int, val after: Int)

class Board(private val rows: Int, private val cols: Int) {
    private val stones: MutableList<MutableList<Int>> = mutableListOf()
    private val stonePlacementStack: Stack<StonePlacement> = Stack<StonePlacement>()

    fun checkIfPointIsEmpty(x: Int, y: Int): Boolean {
        if (y < 0 || y >= rows || x < 0 || x >= cols) {
            return false
        }

        return stones[y][x] == STONE_EMPTY
    }

    private fun checkIfStonePlacementIsFeasible(x: Int, y: Int, stoneType: Int): Boolean {
        if (y < 0 || y >= rows || x < 0 || x >= cols) {
            return false
        }
        if (stoneType != STONE_EMPTY && stoneType != STONE_BLACK && stoneType != STONE_WHITE) {
            return false
        }
        return true
    }

    private fun setStone(x: Int, y: Int, stoneType: Int): StonePlacement? {
        if (!checkIfStonePlacementIsFeasible(x, y, stoneType))
            return null

        val result = StonePlacement(x, y, stones[y][x], stoneType)
        stonePlacementStack.push(StonePlacement(x, y, stones[y][x], stoneType))
        stones[y][x] = stoneType
        return result
    }

    fun tryPlaceStone(x: Int, y: Int, stoneType: Int): StonePlacement? {
        if (!checkIfPointIsEmpty(x, y))
            return null

        return setStone(x, y, stoneType)
    }

    fun getStone(x: Int, y: Int): Int = stones[y][x]

    fun getStonePlacementStack(): List<StonePlacement> = stonePlacementStack.toList()

    init {
        initiateStones(rows, cols)
    }

    private fun initiateStones(rows: Int, cols: Int) {
        stones.clear()
        for (i in 0 until rows) {
            addEmptyStoneRow(cols)
        }
    }

    private fun addEmptyStoneRow(cols: Int) {
        stones.add(mutableListOf())
        for (i in 0 until cols) {
            stones.last().add(STONE_EMPTY)
        }
    }

    private fun getLengthOfSerialOccurrence(x: Int, y: Int, dx: Int, dy: Int, stoneType: Int): Int {
        if (dx == 0 && dy == 0)
            return 0

        var currentX = x + dx
        var currentY = y + dy
        var length = 0
        while (checkIfStonePlacementIsFeasible(currentX, currentY, stoneType)) {
            if (stones[currentY][currentX] != stoneType)
                break
            currentX += dx
            currentY += dy
            length++
        }
        return length
    }

    fun getLongestLineLength(x: Int, y: Int): Int {
        var maxLength = 1
        for (dx in 0..1) {
            for (dy in 0..1) {
                maxLength = max(
                    getLengthOfSerialOccurrence(x, y, dx, dy, stones[y][x])
                            + getLengthOfSerialOccurrence(x, y, -dx, -dy, stones[y][x]) + 1,
                    maxLength
                )
            }
        }
        return maxLength
    }

    companion object {
        const val STONE_EMPTY = 0
        const val STONE_BLACK = 1
        const val STONE_WHITE = 2
    }
}