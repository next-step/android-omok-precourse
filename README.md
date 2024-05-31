# android-omok

- 카카오 테크 캠퍼스 미니과제(오목) 수행을 위한 저장소입니다.

## 구현 화면
1. 게임 시작 / 다시 시작 화면　　　　　2. 게임 중 화면(흑돌 차례)

<img src=https://github.com/ichanguk/android-omok/blob/ichanguk/images/startImage.png width="200" height="350">　　　　　<img src=https://github.com/ichanguk/android-omok/blob/ichanguk/images/playing_black.png width="200" height="350">

3. 게임 중 화면(백돌 차례)　　　　　4. 돌을 둘 곳 미리보기 화면

<img src=https://github.com/ichanguk/android-omok/blob/ichanguk/images/playing_white.png width="200" height="350">　　　　　<img src=https://github.com/ichanguk/android-omok/blob/ichanguk/images/previewImage.png width="200" height="350">

5. 게임 결과 화면(흑돌 승)　　　　　6. 게임 결과 화면(백돌 승)

<img src=https://github.com/ichanguk/android-omok/blob/ichanguk/images/win_black.png width="200" height="350">　　　　　<img src=https://github.com/ichanguk/android-omok/blob/ichanguk/images/win_white.png width="200" height="350">

## feature
1. 보드에 흑돌 / 백돌을 번갈아 둘 수 있는 기능
    - 차례 표시 UI 구현
    - 돌을 둘 때 마다 차례 표시 UI가 바뀌도록 구현

2. 클릭한 곳이 원하는 위치가 맞는 지 확인 후 돌을 둘 수 있는 기능
    - '두기' 버튼 UI 구현
    - 보드의 빈 칸을 클릭하면 점선으로 그 칸을 표시하도록 구현
    - '두기' 버튼을 눌러야 돌이 놓아지고 차례가 넘어가도록 구현

3. 오목 게임 기능
    - 오목이 완성되면 게임이 종료되도록 구현
    - '다시 시작' 버튼 UI 구현
    - '다시 시작' 버튼에 다시 시작 기능 추가
    - 결과 확인과 다시 시작을 위한 팝업창 UI 구현
    - 게임이 종료되면 팝업창이 뜨고 다시 시작할 수 있도록 구현
    