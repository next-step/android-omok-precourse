package nextstep.omok

class Board {
    val MAX_SIZE = 15
    private val board = Array(MAX_SIZE) { Array<Int>(MAX_SIZE) { 0 } }

    fun initBoard() {
        for (i in 0..<MAX_SIZE)
            for (j in 0..<MAX_SIZE)
                board[i][j] = 0
    }

    fun checkIndex(row: Int, col: Int): Boolean {
        return if (row < 0 || row >= MAX_SIZE || col < 0 || col >= MAX_SIZE) {
            print("nullError" + "인덱스 오류")
            false
        } else true
    }

    fun isEmpty(row: Int, col: Int): Boolean? {
        return if (checkIndex(row, col)) board[row][col] == 0
        else null
    }

    fun checkStone(row: Int, col: Int): Int? {
        return if (checkIndex(row, col)) board[row][col]
        else null
    }

    fun placeStone(row: Int, col: Int, player: Int): Boolean {
        return if (isEmpty(row, col) == true) {
            board[row][col] = player
            true
        } else false
    }

    fun isLineOverFive(row: Int, col: Int): Boolean {
        val player = checkStone(row, col)
        if (!(player == 1 || player == -1)) return false
        return isVerticalOverFive(row, col, player)
                || isHorizontalOverFive(row, col, player)
                || isDiagonalOverFive(row, col, player)
    }

    fun isVerticalOverFive(row: Int, col: Int, player: Int): Boolean {
        var count = 1
        for (i in col - 1 downTo 0) {
            if (isSameColor(row, i, player)) count += 1
            else break
        }
        for (i in col + 1..<MAX_SIZE) {
            if (isSameColor(row, i, player)) count += 1
            else break
        }
        return count >= 5
    }

    fun isHorizontalOverFive(row: Int, col: Int, player: Int): Boolean {
        var count = 1
        for (i in row - 1 downTo 0) {
            if (isSameColor(i, col, player)) count += 1
            else break
        }
        for (i in row + 1..<MAX_SIZE) {
            if (isSameColor(i, col, player)) count += 1
            else break
        }
        return count >= 5
    }


    fun isDiagonalOverFive(row: Int, col: Int, player: Int): Boolean {
        return isLowerToUpperDiagonalOverFive(row, col, player)
                || isUpperToLowerDiagonalOverFive(row, col, player)
    }

    fun isLowerToUpperDiagonalOverFive(row: Int, col: Int, player: Int): Boolean {
        val count =
            countUpperRightDiagonal(row, col, player) + countLowerLeftDiagonal(row, col, player) + 1
        return count >= 5
    }

    fun isUpperToLowerDiagonalOverFive(row: Int, col: Int, player: Int): Boolean {
        val count =
            countUpperLeftDiagonal(row, col, player) + countLowerRightDiagonal(row, col, player) + 1
        return count >= 5
    }

    fun countUpperLeftDiagonal(row: Int, col: Int, player: Int): Int {
        var posY = row
        var posX = col
        var count = 0
        while (posY - 1 >= 0 && posX - 1 >= 0) {
            posY -= 1
            posX -= 1
            if (isSameColor(posY, posX, player)) count += 1
            else break
        }
        return count
    }

    fun countUpperRightDiagonal(row: Int, col: Int, player: Int): Int {
        var posY = row
        var posX = col
        var count = 0
        while (posY - 1 >= 0 && posX + 1 < MAX_SIZE) {
            posY -= 1
            posX += 1
            if (isSameColor(posY, posX, player)) count += 1
            else break
        }
        return count
    }

    fun countLowerLeftDiagonal(row: Int, col: Int, player: Int): Int {
        var posY = row
        var posX = col
        var count = 0
        while (posY + 1 < MAX_SIZE && posX - 1 >= 0) {
            posY += 1
            posX -= 1
            if (isSameColor(posY, posX, player)) count += 1
            else break
        }
        return count
    }

    fun countLowerRightDiagonal(row: Int, col: Int, player: Int): Int {
        var posY = row
        var posX = col
        var count = 0
        while (posY + 1 < MAX_SIZE && posX + 1 < MAX_SIZE) {
            posY += 1
            posX += 1
            if (isSameColor(posY, posX, player)) count += 1
            else break
        }
        return count
    }

    fun isSameColor(row: Int, col: Int, player: Int): Boolean {
        val stone = checkStone(row, col)
        return stone == player
    }
}