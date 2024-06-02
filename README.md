# android-omok-precourse

# 오목 게임

이 프로젝트는 안드로이드를 위한 코틀린으로 구현된 간단한 오목(Omok, Gomoku) 게임이다. 
이 게임은 두 명의 플레이어가 15 X 15 오목 판에 검은 돌과 흰 돌을 놓을 수 있게 하며, 전통적인 오목 규칙에 따라 진행된다. 추가로, 흑돌은 금수 규칙이 적용된다.


## 주요 기능

- **두 명의 플레이어**: 흑돌과 백돌.
  
- **기본 오목 규칙**: 가로, 세로 또는 대각선으로 다섯 개의 돌을 먼저 놓으면 승리
- **흑돌 금수 규칙**:
  - "삼삼" (Double Three): 두 개의 열린 삼을 만드는 돌 놓기 금지
    
  - "사사" (Double Four): 두 개의 열린 사를 만드는 돌 놓기 금지
  - "장목" (Overline): 여섯 개 이상의 돌을 연속으로 놓는 것 금지
- **백돌 자유 규칙**: 백돌은 아무 곳에나 놓을 수 있음
- **승리 및 금수 위치 알림**: 승리 조건 충족 시 및 금수 위치에 돌을 놓으려 할 때 토스트 메시지로 알림



## 파일 설명

### MainActivity.kt

`MainActivity`는 안드로이드 액티비티로서, 게임 보드를 설정하고 사용자 입력을 처리한다. 게임의 주요 로직은 `OmokGame` 클래스로 구현한다.

### OmokGame.kt

`OmokGame` 클래스는 게임의 상태를 관리하고, 금수 규칙과 승리 조건을 확인하는 로직을 포함한다.

### GameLogicTest.kt

`GameLogicTest` 클래스는 JUnit 5와 AssertJ를 사용하여 `OmokGame`의 주요 기능을 테스트. 삼삼, 사사, 장목 금수 규칙과 승리 조건을 검증한다.
