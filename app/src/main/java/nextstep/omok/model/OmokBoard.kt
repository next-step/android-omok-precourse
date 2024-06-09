package nextstep.omok.model

object OmokBoard {
    private val omokBoard: MutableList<MutableList<IntersectionState>> = mutableListOf()

    init {
        for(i in 0..14) {
            val boardRow = mutableListOf<IntersectionState>()
            for(j in 0..14) {
                boardRow.add(IntersectionState.Empty)
            }
            omokBoard.add(boardRow)
        }
    }

    fun updateBoard(rowIndex: Int, colIndex: Int, stone: IntersectionState) {
        omokBoard[rowIndex][colIndex] = stone
    }

    fun getBoard(): List<List<IntersectionState>> {
        return omokBoard
    }

    fun getBoardIntersection(row: Int, col: Int): IntersectionState {
        return omokBoard[row][col]
    }
}