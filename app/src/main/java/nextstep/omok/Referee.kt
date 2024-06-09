class Referee(private val boardSize: Int, private val boardState: Array<Int?>) {

    fun checkState(position: Int): Boolean {
        if (position !in boardState.indices) return false

        val row = position / boardSize
        val column = position % boardSize
        val stone = boardState[position] ?: return false

        return checkVertical(row, column, stone) || checkHorizontal(row, column, stone) || checkDiagonal(row, column, stone)
    }

    private fun checkVertical(row: Int, column: Int, stone: Int): Boolean {
        val topCount = (row - 1 downTo 0).takeWhile { boardState[it * boardSize + column] == stone }.count()
        val bottomCount = (row + 1 until boardSize).takeWhile { boardState[it * boardSize + column] == stone }.count()

        val count = topCount + bottomCount

        return (count >= 4)
    }

    private fun checkHorizontal(row: Int, column: Int, stone: Int): Boolean {
        val leftCount = (column - 1 downTo 0).takeWhile { boardState[row * boardSize + it] == stone }.count()
        val rightCount = (column + 1 until boardSize).takeWhile { boardState[row * boardSize + it] == stone }.count()

        val count = leftCount + rightCount

        return (count >= 4)
    }

    private fun checkDiagonal(row: Int, column: Int, stone: Int): Boolean {
        // 왼->오 대각선
        val leftTopCount = (1..minOf(row, column)).takeWhile { boardState[(row - it) * boardSize + (column - it)] == stone }.count()
        val rightBottomCount = (1..minOf(boardSize - row - 1, boardSize - column - 1)).takeWhile { boardState[(row + it) * boardSize + (column + it)] == stone }.count()

        // 오->왼 대각선
        val rightTopCount = (1..minOf(row, boardSize - column - 1)).takeWhile { boardState[(row - it) * boardSize + (column + it)] == stone }.count()
        val leftBottomCount = (1..minOf(boardSize - row - 1, column)).takeWhile { boardState[(row + it) * boardSize + (column - it)] == stone }.count()

        val countd1 = leftTopCount + rightBottomCount
        val countd2 = rightTopCount + leftBottomCount

        return (countd1 >= 4 || countd2 >= 4)
    }
}
