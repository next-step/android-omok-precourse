# android-omok-precourse

오목은 두 사람이 번갈아 돌을 놓아 가로나 세로, 대각선으로 다섯 개의 연속된 돌을 먼저 만들면 승리하는 게임이다
초기 코드를 실행해보면 1) 오목판이 구현되어 있으며 2) 흑돌을 원하는 위치에 둘 수 있는 상황이다

## 구현할 기능
1. 오목판 초기화(initializeBoard)
2. 돌 번갈아 놓기(onCellClikced, placeStone)
3. 예외 처리 - 같은 위치에 돌을 놓지 못하게 하기(isCellOccupied)
4. 승리 조건 확인(checkWin/countStones)
5. 게임 종료 후 보드 초기화(resetBoard)