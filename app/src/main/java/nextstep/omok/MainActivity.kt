package nextstep.omok

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children

class MainActivity : AppCompatActivity() {
    private var isBlackTurn = true
    private var readyToPlace = false
    private val NO_STONE = 0
    private val BLACK_STONE = 1
    private val WHITE_STONE = 2

    private lateinit var board: TableLayout
    private lateinit var blackTurnImageView: ImageView
    private lateinit var whiteTurnImageView: ImageView
    private lateinit var placeStoneBtn: Button
    private lateinit var previewedCell: ImageView
    private lateinit var boardList: MutableList<MutableList<Int>>
    private lateinit var pointToPlace: Pair<Int, Int>
    private lateinit var deltaList: MutableList<Pair<Int, Int>>
    private lateinit var restartBtn: Button
    private lateinit var resultTextView: TextView
    private lateinit var gameOverRestartBtn: Button
    private lateinit var winnerImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVariables()
        setClickListeners()
    }

    /**
     * 변수와 뷰를 초기화.
     * - `board`: 오목 보드 뷰.
     * - `blackTurnImageView`: 흑돌 차례를 나타내는 이미지 뷰.
     * - `whiteTurnImageView`: 백돌 차례를 나타내는 이미지 뷰.
     * - `placeStoneBtn`: 돌을 보드에 두는 버튼.
     * - `restartBtn`: 다시 시작을 위한 버튼.
     * - `boardList` : 보드의 각 칸에 놓인 돌을 저장(black : 1, white : 2, null : 0)
     * - 'deltaList' : 오목이 완성됐는 지 체크하기 위한 좌표 변화량 리스트
     */
    private fun initVariables() {
        board = findViewById(R.id.board)
        blackTurnImageView = findViewById(R.id.black_turn_image)
        whiteTurnImageView = findViewById(R.id.white_turn_image)
        placeStoneBtn = findViewById(R.id.place_stone_btn)
        restartBtn = findViewById(R.id.restart_btn)

        boardList = MutableList(15) { MutableList(15) { NO_STONE } }
        deltaList = mutableListOf(
            Pair(-1, 0), Pair(1, 0), // 세로
            Pair(0, -1), Pair(0, 1), // 가로
            Pair(-1, -1), Pair(1, 1), // '\' 대각선 방향
            Pair(-1, 1), Pair(1, -1)   // '/' 대각선 방향
        )
    }

    /**
     * 클릭 리스너들을 설정하는 함수
     */
    private fun setClickListeners() {
        setOnClickListenerForStones()
        setOnClickListenerForPlaceStoneBtn()
        setOnClickListenerForRestartBtn()
    }

    /**
     * 다시 시작 버튼의 클릭 리스너를 설정하는 함수.
     */
    private fun setOnClickListenerForRestartBtn() {
        restartBtn.setOnClickListener {
            restart()
        }
    }

    /**
     * 게임을 다시 시작하는 함수.
     * 변수와 view들을 reset.
     */
    private fun restart() {
        resetVariables()
        resetBoardView()
        changeTurnImage()
    }


    /**
     * 처음 상태로 변수들을 reset하는 함수.
     */
    private fun resetVariables() {
        boardList = MutableList(15) { MutableList(15) { NO_STONE } }
        isBlackTurn = true
    }

    /**
     * 보드의 이미지(돌)들을 모두 비우는 함수.
     */
    private fun resetBoardView() {
        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { cell -> cell.setImageDrawable(null) }
    }

    /**
     * turn을 표시하는 image의 visibility를 바꿔주는 함수.
     */
    private fun changeTurnImage() {
        if (isBlackTurn) {
            whiteTurnImageView.visibility = View.INVISIBLE
            blackTurnImageView.visibility = View.VISIBLE
        } else {
            blackTurnImageView.visibility = View.INVISIBLE
            whiteTurnImageView.visibility = View.VISIBLE
        }
    }

    /**
     * 보드의 각 칸(이미지 뷰)에 클릭 리스너 설정.
     */
    private fun setOnClickListenerForStones() {
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, row ->
                setOnClickListenerForRow(row, rowIndex)
            }
    }

    /**
     * 보드의 각 칸(이미지 뷰)에 클릭 리스너 설정.
     * 칸을 클릭하면 해당 칸이 맞는 지 미리 보여주는 동작 수행.
     *
     * @param row 클릭 리스너를 설정할 행
     * @param rowIndex 클릭 리스너를 설정할 행의 index
     */
    private fun setOnClickListenerForRow(row: TableRow, rowIndex: Int) {
        row.children
            .filterIsInstance<ImageView>()
            .forEachIndexed { colIndex, cell ->
                cell.setOnClickListener { previewStone(cell, rowIndex, colIndex) }
            }
    }

    /**
     * 칸을 클릭하면 해당 칸이 맞는 지 미리 보여주는 동작 수행.
     * 놓을 위치를 변경 하고자 할 땐 이전 칸의 미리 보기 삭제.
     * 칸에 돌이 없을 때만 동작.
     *
     * - `readyToPlace`: 현재 미리보기가 보여지고 있는 경우 true 아니면 false
     * - `previewedCell`: 미리보기 중인 칸의 ImageView
     * - `pointTopPlace`: 미리보기 중인 칸의 xy 좌표 쌍
     *
     * @param cell 클릭한 칸을 나타내는 이미지 뷰
     * @param rowIndex 클릭한 칸의 행 index
     * @param colIndex 클린한 칸의 열 index
     */
    private fun previewStone(cell: ImageView, rowIndex: Int, colIndex: Int) {
        if (readyToPlace) {
            previewedCell.setImageDrawable(null)
        }
        if (boardList[rowIndex][colIndex] == NO_STONE) {
            cell.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dashed_stone))
            readyToPlace = true
            previewedCell = cell
            pointToPlace = Pair(rowIndex, colIndex)
        }
    }

    /**
     * '두기' 버튼에 대한 클릭 리스너 설정.
     * 돌을 둘 칸을 선택한 경우 돌을 둠.
     * 돌을 둘 칸을 선택하지 않은 경우 안내 메시지를 Toast.
     */
    private fun setOnClickListenerForPlaceStoneBtn() {
        placeStoneBtn.setOnClickListener {
            if (readyToPlace) {
                placeStone()
                readyToPlace = false
            } else {
                Toast.makeText(this, "먼저 돌을 둘 곳을 클릭하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 차례에 맞게 선택한 칸에 돌을 두는 함수.
     * 돌을 두고 나면 오목이 완성 됐는지 체크.
     * 오목이 완성이 안 됐다면 차례를 바꿈.
     */
    private fun placeStone() {
        val stoneType = if (isBlackTurn) BLACK_STONE else WHITE_STONE
        val stoneDrawable = if (isBlackTurn) R.drawable.black_stone else R.drawable.white_stone

        previewedCell.setImageResource(stoneDrawable)
        boardList[pointToPlace.first][pointToPlace.second] = stoneType

        if (checkForCompleteOmok(stoneType)) {
            showGameOverDialog(stoneType)
            return
        }
        isBlackTurn = !isBlackTurn
        changeTurnImage()
    }

    /**
     * 한 방향이라도 오목이 완성 됐는지 확인하는 함수.
     * 세로, 가로, \ 대각선, / 대각선 방향으로 순회하며 체크
     *
     * @param stoneType 현재 차례를 알려주는 변수(흑돌 : 1, 백돌: 2)
     * @return 오목이 완성된 경우 true, 아닌 경우 false
     */
    private fun checkForCompleteOmok(stoneType: Int): Boolean {
        for (i in 0 until 4) {
            if (completeOmok(2 * i, stoneType)) {
                return true
            }
        }
        return false
    }

    /**
     * 오목이 완성 됐는지 확인하는 함수
     *
     * @param deltaIndex deltaList 중 시작할 Index
     * @param stoneType 확인할 돌의 유형 값
     * @return 오목이 완성 된 경우 true 아니면 false
     */
    private fun completeOmok(deltaIndex: Int, stoneType: Int): Boolean {
        return countStone(deltaList[deltaIndex], stoneType) + countStone(deltaList[deltaIndex + 1], stoneType) >= 4
    }

    /**
     * 현재 방향에 놓여진 자신의 돌을 세는 함수.
     *
     * @param deltaXY 가야할 방향에 대한 xy 좌표 pair
     * @param turn 현재 차례를 알려 주는 변수(흑돌 : 1, 백돌: 2)
     * @return 현재 방향에 놓여진 자신의 돌의 수
     */
    private fun countStone(deltaXY: Pair<Int, Int>, turn: Int): Int {
        var count = 0
        var curX = pointToPlace.first + deltaXY.first
        var curY = pointToPlace.second + deltaXY.second

        while (isInBoard(curX, curY) && isMyStone(curX, curY, turn)) {
            count++
            curX += deltaXY.first
            curY += deltaXY.second
        }
        return count
    }

    /**
     * 현재 좌표가 board 범위 안에 있는 좌표인지 체크하는 함수.
     *
     * @param curX 현재 x 좌표
     * @param curY 현재 y 좌표
     * @return 현재 좌표가 범위 안에 있으면 true 아니면 false
     */
    private fun isInBoard(curX: Int, curY: Int): Boolean {
        return (curX in 0 until 15 && curY in 0 until 15)
    }

    /**
     * 현재 좌표에 놓인 돌이 본인의 돌인지 확인 하는 함수.
     *
     * @param curX 현재 x 좌표
     * @param curY 현재 y 좌표
     * @param stoneType 현재 차례 돌 유형
     * @return 현재 좌표의 돌이 본인의 돌이면 true 아니면 false
     */
    private fun isMyStone(curX: Int, curY: Int, stoneType: Int): Boolean {
        return boardList[curX][curY] == stoneType
    }


    /**
     * 게임 종료 시 대화상자를 보여주는 함수.
     * - `resultTextView`: 결과 메시지를 보여주는 text view.
     * - `gameOverRestartBtn`: 다시 시작을 위한 button.
     * - `window`: 대화 상자 window 객체
     * - `layoutParams`: 대화 상자의 속성을 설정하기 위한 객체
     *
     * @param stoneType 승자가 백돌인지 흑돌인지를 나타내는 변수
     */
    private fun showGameOverDialog(stoneType: Int) {
        val dialog = Dialog(this)
        setGameOverDialog(dialog)

        initDialogViews(dialog)
        setDialogViews(dialog, stoneType)

        val window = dialog.window
        val layoutParams = WindowManager.LayoutParams()
        setLayoutParams(layoutParams, window!!)
        window.attributes = layoutParams
        dialog.show()
    }

    /**
     * 대화 상자에 사용되는 view들을 초기화하는 함수.
     *
     * @param dialog gameOverDialog
     */
    private fun initDialogViews(dialog: Dialog) {
        winnerImageView = dialog.findViewById(R.id.winner_image)
        resultTextView = dialog.findViewById(R.id.result_textview)
        gameOverRestartBtn = dialog.findViewById(R.id.game_over_restart_btn)
    }

    /**
     * 대화 상자에 사용되는 view들을 설정하는 함수.
     *
     * @param dialog gameOverDialog
     * @param stoneType 승자가 백돌인지 흑돌인지를 나타내는 변수
     */
    private fun setDialogViews(dialog: Dialog, stoneType: Int) {
        setImageForWinnerImageView(stoneType)
        setTextForResultTextView(stoneType)
        setOnClickListenerForGameOverRestartBtn(dialog)
    }

    /**
     * 승자에 따라 winnerImageView의 이미지를 설정하는 함수.
     *
     * @param stoneType 승자가 백돌인지 흑돌인지를 나타내는 변수
     */
    private fun setImageForWinnerImageView(stoneType: Int) {
        if (stoneType == BLACK_STONE) {
            winnerImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.black_stone))
        } else {
            winnerImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.white_stone))
        }
    }

    /**
     * 게임 결과 text view의 text를 설정하는 함수.
     *
     * @param stoneType 승자가 백돌인지 흑돌인지를 나타내는 변수
     */
    private fun setTextForResultTextView(stoneType: Int) {
        if (stoneType == BLACK_STONE) {
            resultTextView.text = "흑돌 승리!!"
        } else {
            resultTextView.text = "백돌 승리!!"
        }
    }

    /**
     * gameOverDialog를 설정하는 함수.
     *
     * @param dialog gameOverDialog
     */
    private fun setGameOverDialog(dialog: Dialog) {
        dialog.setContentView(R.layout.dialog_game_over)
        dialog.setTitle("게임 종료")
        dialog.setCancelable(false)
    }

    /**
     * 대화상자의 다시 시작 버튼에 클릭 리스너를 설정하는 함수.
     * 클릭하면 대화상자를 닫고 게임이 재시작 되도록 한다.
     *
     * @param dialog gameOverDialog
     */
    private fun setOnClickListenerForGameOverRestartBtn(dialog: Dialog) {
        gameOverRestartBtn.setOnClickListener {
            dialog.dismiss()
            restart()
        }
    }

    /**
     * 대화 상자의 속성을 설정하기 위한 함수.
     *
     * @param layoutParams 대화 상자의 속성을 설정하기 위한 객체
     * @param window 대화 상자 window 객체
     */
    private fun setLayoutParams(layoutParams:WindowManager.LayoutParams, window: Window) {
        layoutParams.copyFrom(window.attributes)
        layoutParams.gravity = Gravity.BOTTOM
        layoutParams.width = 700
        layoutParams.height = 500
        layoutParams.y = 250
    }
}