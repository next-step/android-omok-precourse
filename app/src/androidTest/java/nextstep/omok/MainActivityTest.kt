package nextstep.omok
import org.junit.Assert.assertEquals
import org.junit.Test

class OmokSequenceTest {

    @Test
    fun sequenceTest() {

        val board = MutableList(15) { MutableList(15) { 0 } }


        val testCases = listOf(
            TestCase(0, 0, 0, 1, 0),
            TestCase(0, 0, 1, 0, 0),
            TestCase(0, 0, 1, 1, 0),
            TestCase(0, 0, -1, 1, 0),
            TestCase(0, 0, 0, 0, 0),
            TestCase(0, 0, 0, -1, 0),
            TestCase(0, 0, -1, 0, 0),
            TestCase(0, 0, -1, -1, 0)
        )

        // 테스트 실행 및 결과 확인
        testCases.forEachIndexed { index, testCase ->
            val result = sequenceLeftRightCheck(testCase.startX, testCase.startY, testCase.directionX, testCase.directionY)
            assertEquals(testCase.expectedCount, result) { "Test case #$index failed" }
        }
    }

    // TestCase 클래스 정의
    data class TestCase(val startX: Int, val startY: Int, val directionX: Int, val directionY: Int, val expectedCount: Int)
}
