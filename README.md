# android-omok-precourse

### 📜 Description

오목은 두 사람이 번갈아 돌을 놓아 가로나 세로, 대각선으로 다섯 개의 연속된 돌을 먼저 만들면 승리하는 게임이다.

안드로이드 프레임워크를 사용하여 모바일 앱으로 구현한다


### 🎯 Tasks

- onCreate

액티비티가 생성될 때 게임을 초기화하고 시작 메시지를 표시


- initializeGame

게임을 초기화


- setupBoard

오목판을 설정하고 각 셀에 클릭 리스너를 추가


- placeStone

돌을 놓는 로직 처리


- setImageForCurrentPlayer

현재 플레이어의 돌 이미지를 설정


- handleWin

플레이어가 이겼을 때의 로직을 관리


- switchPlayer

플레이어를 전환하고 메시지를 표시


- checkWin

승리 조건을 확인


- checkDirection

특정 방향으로 연속된 돌의 개수를 확인


- countStones

특정 방향과 그 반대 방향으로 연속된 돌의 수를 합산


- countDirection

주어진 방향으로 연속된 돌의 개수를 계산


- showDialog

게임 오버 다이얼로그를 표시


- restartGame

게임을 재시작


- clearBoard

오목판을 초기화


- showMessage

토스트 메시지를 표시