import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

class DayThree {
    private val input = listOf(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010"
    )

    @Test
    fun gamma() {
        val gamma = input.gamma()
        assertThat(gamma, equalTo(22))
    }

    @Test
    fun epsilon() {
        val epsilon = input.epsilon()
        assertThat(epsilon, equalTo(9))
    }

    @Test
    fun `power is epsilon multiplied by gamma`() {
        assertThat(input.power(), equalTo(198))
    }
}

fun String.asInt(): Int = Integer.parseInt(this, 2)

fun List<Char>.countOf(c: Char) = this.filter { it == c }.size

fun List<Char>.mostCommon(): Char = if (this.countOf('1') > this.countOf('0')) '1' else '0'

fun List<Char>.leastCommon(): Char = if (this.mostCommon() == '1') '0' else '1'

fun List<String>.power(): Int = this.gamma() * this.epsilon()

fun List<String>.slice(idx: Int): List<Char> = this.map { it[idx] }

fun List<String>.gamma(): Int =
    (0..4).map { this.slice(it).mostCommon() }.joinToString(separator = "").asInt()

fun List<String>.epsilon(): Int =
    (0..4).map { this.slice(it).leastCommon() }.joinToString(separator = "").asInt()

