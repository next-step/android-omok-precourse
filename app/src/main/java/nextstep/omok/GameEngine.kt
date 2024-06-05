package nextstep.omok
// Model
class GameEngine(private val view: GameView) {
    val BOARD_SIZE = 15
    private val OMOK_COUNT = 5

    lateinit var blackPlayer: Player
    lateinit var whitePlayer: Player
    lateinit var currentPlayer: Player
    private var turnCount: Int = 0

    private val boardStatus = Array(BOARD_SIZE) { Array<Stone?>(BOARD_SIZE) { null } }

    fun startGame() {
        initializePlayers()
        view.initializeBoard()
        view.updatePlayerFlag(currentPlayer)
    }

    private fun initializePlayers() {
        val blackStone = BlackStone()
        val whiteStone = WhiteStone()
        blackPlayer = Player("Black", blackStone, blackStone.highlightedResId)
        whitePlayer = Player("White", whiteStone, whiteStone.highlightedResId)
        currentPlayer = blackPlayer
    }


    fun onCellClick(cell: Cell) {
        turnCount++
        cell.position?.let { (row, col) ->
            boardStatus[row][col] = currentPlayer.stone
            checkGameEnd(cell)
        }
    }


    private fun checkGameEnd(cell: Cell) {
        val isWin = isWinningMove(cell)
        val isDraw = isDraw()

        if (isWin || isDraw) {
            val message = if (isWin) "${currentPlayer.stone}이 승리했습니다." else "무승부입니다."
            view.displayEnding(message)
        } else {
            switchPlayer()
            view.updatePlayerFlag(currentPlayer)
        }
    }


    fun isDraw(): Boolean {
        return turnCount == BOARD_SIZE * BOARD_SIZE
    }


    private val directions = listOf(
        Direction(1, 0),  // horizontal
        Direction(0, 1),  // vertical
        Direction(1, 1),  // diagonal down-right
        Direction(1, -1)  // diagonal down-left
    )

    private fun isWinningMove(cell: Cell): Boolean {
        val (row, col) = cell.position?: return false
        return directions.any { (dx, dy) ->
            val count = 1 + countStones(row, col, dx, dy) + countStones(row, col, -dx, -dy)
            count >= OMOK_COUNT
        }
    }


    private fun countStones(row: Int, col: Int, dx: Int, dy: Int): Int {
        var nextRow = row + dx
        var nextCol = col + dy

        if (isWithinBounds(nextRow, nextCol) && isSamePlayerStone(nextRow, nextCol)) {
            return 1 + countStones(nextRow, nextCol, dx, dy)
        }
        return 0
    }

    private fun isWithinBounds(row: Int, col: Int): Boolean {
        return row in 0 until BOARD_SIZE && col in 0 until BOARD_SIZE
    }

    private fun isSamePlayerStone(row: Int, col: Int): Boolean {
        return boardStatus[row][col] == currentPlayer.stone
    }

    private fun switchPlayer() {
        currentPlayer = when (currentPlayer) {
            blackPlayer -> whitePlayer
            else -> blackPlayer
        }
    }

}