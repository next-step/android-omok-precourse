# android-omok-precourse
<Project Name>
Omok Application

<How To Start>
백돌이 먼저 오목 판에 돌을 놓으면서 시작

<features>
1. switchPlayer : currentPlayer을 "w"에서 시작, 돌을 놓고 나서 winner switch
2. initializeBoard : 돌을 놓을 위치에 click listener 설정하여 위치를 누를 때 마다 onCellClick 함수 실행
3. onCellClicked : 돌이 놓였을 때 비어있는 위치라면 돌을 놓고 돌을 놓을 수 없다면 사용자에게 메시지를 남긴다
    - 돌을 놓은 후 승리조건 확인 : checkWin (T 이면 showWinner)
4. showWinner : 승리 조건에 만족하면 새 activity가 열리면서 흑/백돌 win! 출력
    - WinnerActivity
5. checkWin : 가로, 세로, 두 가지 대각선에 대해 5개의 돌이 연속으로 있는지 확인 (T,F반환)
    - [1][0], [0][1], [1][1], [1][-1] 로 각 방향 지정
    - checkDirection : 오/왼 방향 모두 개수 세기, 5이상이면 T, else F
    - countStones: direction따라 돌 개수 세기

<run tests>
JUnit5 / AssertJ

<credit>
khyeonm