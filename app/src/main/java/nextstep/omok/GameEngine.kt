package nextstep.omok

// Model
class GameEngine(private val view: GameView) {
    enum class GameState {
        ONGOING,
        WIN,
        DRAW,
    }

    var gameState: GameState = GameState.ONGOING
        private set

    val boardSize = 15
    val omokCount = 5

    lateinit var blackPlayer: Player
    lateinit var whitePlayer: Player
    lateinit var currentPlayer: Player
    private var moveCount: Int = 0

    var boardStatus = Array(boardSize) { Array<Stone?>(boardSize) { null } }

    fun startGame() {
        try {
            initializePlayers()
            view.initializeBoard()
            view.updatePlayerFlag(currentPlayer)
        } catch (e: Exception) {
            view.displayEnding("게임을 시작하는 동안 오류가 발생했습니다: ${e.message}")
        }
    }

    fun resetGame() {
        moveCount = 0
        gameState = GameState.ONGOING
        boardStatus = Array(boardSize) { Array<Stone?>(boardSize) { null } }
        startGame()
    }

    fun isGameOver(): Boolean {
        return gameState != GameState.ONGOING
    }

    private fun initializePlayers() {
        val blackStone = BlackStone()
        val whiteStone = WhiteStone()
        blackPlayer = Player("Black", blackStone, blackStone.highlightedResId)
        whitePlayer = Player("White", whiteStone, whiteStone.highlightedResId)
        currentPlayer = blackPlayer
    }

    fun handleCellClick(cell: Cell) {
        moveCount++
        cell.position?.let { (row, col) ->
            boardStatus[row][col] = currentPlayer.stone
            handleTurnResult(cell)
        }
    }

    private fun handleTurnResult(cell: Cell) {
        when {
            isWin(cell) -> {
                gameState = GameState.WIN
                val message = "${currentPlayer.stone}이 승리했습니다."
                view.displayEnding(message)
            }
            isDraw() -> {
                gameState = GameState.DRAW
                view.displayEnding("무승부입니다.")
            }
            else -> {
                switchPlayer()
                view.updatePlayerFlag(currentPlayer)
            }
        }
    }

    fun isDraw(): Boolean {
        return moveCount == boardSize * boardSize
    }

    // horizontal, vertical, diagonal down-right, diagonal down-left
    private val directions =
        listOf(
            Direction(1, 0),
            Direction(0, 1),
            Direction(1, 1),
            Direction(1, -1),
        )

    fun isWin(cell: Cell): Boolean {
        val (row, col) = cell.position ?: return false
        return directions.any { (dx, dy) ->
            val count = 1 + countStones(row, col, dx, dy) + countStones(row, col, -dx, -dy)
            count >= omokCount
        }
    }

    fun countStones(
        row: Int,
        col: Int,
        dx: Int,
        dy: Int,
    ): Int {
        var nextRow = row + dx
        var nextCol = col + dy

        if (isWithinBounds(nextRow, nextCol) && isCurrentPlayerStone(nextRow, nextCol)) {
            return 1 + countStones(nextRow, nextCol, dx, dy)
        }
        return 0
    }

    fun isWithinBounds(
        row: Int,
        col: Int,
    ): Boolean {
        return row in 0 until boardSize && col in 0 until boardSize
    }

    fun isCurrentPlayerStone(
        row: Int,
        col: Int,
    ): Boolean {
        return boardStatus[row][col] == currentPlayer.stone
    }

    private fun switchPlayer() {
        currentPlayer =
            when (currentPlayer) {
                blackPlayer -> whitePlayer
                else -> blackPlayer
            }
    }
}
