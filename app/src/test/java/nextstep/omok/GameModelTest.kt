package nextstep.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameModelTest {

    @BeforeEach
    fun setUp() {
        // 각 테스트 전에 게임을 초기화
        GameModel.resetGame()
    }

    @Test
    fun `resetGame should initialize the board and currentPlayer`() {
        // 게임이 초기화되면 보드와 현재 플레이어를 확인
        GameModel.resetGame()
        assertThat(GameModel.board.all { row -> row.all { it == null } }).isTrue
        assertThat(GameModel.currentPlayer).isEqualTo(Player.BLACK)
    }

    @Test
    fun `placeStone should place stone and switch player B to W if valid`() {
        // 돌을 놓을 수 있으면 돌을 놓고 플레이어를 전환 (블랙->화이트)
        val x = 7
        val y = 7
        assertThat(GameModel.placeStone(x, y)).isTrue
        assertThat(GameModel.board[x][y]).isEqualTo(Player.BLACK)
        GameModel.switchPlayer()
        assertThat(GameModel.currentPlayer).isEqualTo(Player.WHITE)
    }

    @Test
    fun `placeStone should place stone and switch player W to B if valid`() {
        // 돌을 놓을 수 있으면 돌을 놓고 플레이어를 전환 (화이트->블랙)
        val x = 2
        val y = 2
        assertThat(GameModel.switchPlayer())
        assertThat(GameModel.placeStone(x, y)).isTrue
        assertThat(GameModel.board[x][y]).isEqualTo(Player.WHITE)
        GameModel.switchPlayer()
        assertThat(GameModel.currentPlayer).isEqualTo(Player.BLACK)
    }

    @Test
    fun `placeStone should not place stone if already occupied`() {
        // 이미 돌이 놓여진 위치에는 돌을 놓을 수 없음
        val x = 7
        val y = 7
        GameModel.placeStone(x, y)
        assertThat(GameModel.placeStone(x, y)).isFalse
    }

    @Test
    fun `checkWinCondition should detect a win`() {
        // 가로로 5개를 놓아서 승리 조건을 충족시킴
        val x = 8
        for (y in 0..4) {
            GameModel.placeStone(x, y)
        }
        assertThat(GameModel.checkWinCondition(x, 4)).isTrue
    }

    @Test
    fun `checkDirection should detect vertical win`() {
        val y = 5
        for (x in 0..4) {
            GameModel.placeStone(x, y)
        }
        assertThat(GameModel.checkWinCondition(4, y)).isTrue
    }

    @Test
    fun `checkDirection should detect positive diagonal win`() {
        for (i in 0..4) {
            GameModel.placeStone(i, i)
        }
        assertThat(GameModel.checkWinCondition(4, 4)).isTrue
    }

    @Test
    fun `checkWinCondition should not detect a win incorrectly`() {
        // 승리 조건을 충족하지 않도록 돌을 놓음
        val x = 7
        val y = 7
        GameModel.placeStone(x, y)
        GameModel.switchPlayer()
        GameModel.placeStone(x, y + 1)
        assertThat(GameModel.checkWinCondition(x, y)).isFalse
    }

    @Test
    fun `switchPlayer should switch between black and white players`() {
        // 플레이어 전환 로직 확인
        assertThat(GameModel.currentPlayer).isEqualTo(Player.BLACK)
        GameModel.switchPlayer()
        assertThat(GameModel.currentPlayer).isEqualTo(Player.WHITE)
        GameModel.switchPlayer()
        assertThat(GameModel.currentPlayer).isEqualTo(Player.BLACK)
    }

    @Test
    fun `getCurrentPlayerStoneResId should return correct resource id`() {
        // 현재 플레이어의 돌 리소스 ID 확인
        assertThat(GameModel.getCurrentPlayerStoneResId()).isEqualTo(Player.BLACK.stoneResId)
        GameModel.switchPlayer()
        assertThat(GameModel.getCurrentPlayerStoneResId()).isEqualTo(Player.WHITE.stoneResId)
    }

    @Test
    fun `handleWin should set the winner correctly`() {
        // 승리 처리가 정상적으로 수행되는지 확인
        GameModel.handelWin()
        assertThat(GameModel.winner).isEqualTo(Player.BLACK)
    }
}
