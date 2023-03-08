import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

/**
 * https://adventofcode.com/2021/day/1
 */

class DayOne {
    @Test
    fun `depth scanning`() {
        val input = listOf(199, 200, 208, 210, 200, 207, 240, 269, 260, 263)
        val expected = listOf(
            "increased",
            "increased",
            "increased",
            "decreased",
            "increased",
            "increased",
            "increased",
            "decreased",
            "increased"
        )

        val actual = input
            .windowed(2, 1)
            .map { depthPair -> if (depthPair[0] > depthPair[1]) "decreased" else "increased" }

        assertThat(actual, equalTo(expected))
    }
}