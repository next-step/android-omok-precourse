# android-omok-precourse

안드로이드 기반의 오목 게임 애플리케이션을 구현한 것입니다. 기본적인 게임 규칙에 따라, 두 플레이어(흑돌과 백돌)가 번갈아가며 돌을 놓고, 가로, 세로, 대각선 방향 중 하나로 연속된 다섯 돌을 먼저 놓는 플레이어가 승리하는 게임입니다. 

기능 소개
1. 게임 보드 초기 설정
setupBoard(): 게임 보드를 초기화하고, 각 셀에 클릭 리스너를 설정합니다.
2. 돌 놓기
handleCellClick(rowIndex: Int, columnIndex: Int): 사용자가 셀을 클릭했을 때 호출됩니다. 해당 위치에 돌을 놓을 수 있는지 확인하고, 가능하다면 돌을 놓고 게임 상태를 업데이트합니다.
3. 돌 놓기 상태 업데이트
placeStone(rowIndex: Int, columnIndex: Int): 선택된 위치에 돌을 놓습니다. 이미 돌이 있으면 동작하지 않습니다.
updateBoardView(rowIndex: Int, columnIndex: Int): 보드의 뷰를 업데이트합니다. 돌을 놓은 위치에 해당하는 이미지를 변경합니다.
4. 승리 조건 확인
checkWin(rowIndex: Int, columnIndex: Int): 현재 돌을 놓은 위치를 기준으로 승리 조건을 만족하는지 확인합니다.
checkDirection(row: Int, col: Int, dRow: Int, dCol: Int): 특정 방향으로 연속된 돌의 개수를 세어 승리 조건을 충족하는지 확인합니다.
5. 플레이어 전환
togglePlayer(): 현재 플레이어를 전환합니다. 흑돌이 놓인 후에는 백돌 차례가 되며, 그 반대도 마찬가지입니다.
6. 게임 초기화
resetBoard(): 게임 보드를 초기 상태로 리셋합니다. 모든 돌을 제거하고, 첫 번째 플레이어를 흑돌로 설정합니다.
7. 게임 종료
endGame(): 게임이 종료되었을 때 호출됩니다. 승리 메시지를 표시하고, 게임 보드를 초기화합니다.