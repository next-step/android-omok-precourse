# android-omok-precourse
이 프로젝트는 GridLayout을 사용하여 오목 게임을 구현하였다.
검은색 Player부터 교대로 돌을 놓으며, 가로, 세로, 대각선으로 다섯 개의 돌을 연속으로 놓으면 승리한다
승리하면 승리한 Player를 알리고 종료한다

# 기능
- 15x15 GridLayout 바둑판 그리기
- 흑돌과 백돌 교대로 착수하기
- 오목 승리 조건 감지하기
- 승자를 알리는 다이얼로그 박스를 표시하고 게임 종료

# 코드 개요
- setImgviewListener : 바둑판 위에 바둑돌을 올려놓기 위해 GridLayout의 각 셀에 clickListener 등록하기
- handleStonePlacement : 바둑돌 놓는 로직을 우선 처리하고 승리 조건 체크한다
- isValidPosition : 클릭한 셀이 범위 내에 있는지/비어있는지 확인한다
- placeStone : 해당 셀에 바둑돌을 놓고 UI 업데이트
- checkWin : 최근 클릭이벤트가 승리 조건을 만족하는지 확인한다
- countStonesInDirection : 특정 방향으로 연속된 돌의 개수 세기
- showWinMsg : Winner를 알리는 다이얼로그를 표시하고 Activity를 종료함

# 테스트
nextstep.omok(androidTest) -> GameTest.kt 파일