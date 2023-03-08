import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import kotlin.math.pow

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
        val gamma = input.map { it.asInt() }.gamma()
        assertThat(gamma, equalTo(22))
    }

    @Test
    fun epsilon() {
        val epsilon = input.map { it.asInt() }.epsilon()
        assertThat(epsilon, equalTo(9))
    }

    @Test
    fun `power is epsilon multiplied by gamma`() {
        val epsilon = input.map { it.asInt() }.epsilon()
        val gamma = input.map { it.asInt() }.gamma()
        assertThat(gamma * epsilon, equalTo(198))

    }

    @Test
    fun `string to int conversion`() {
        assertThat("000000".asInt(), equalTo(0))
        assertThat("001000".asInt(), equalTo(8))
        assertThat("001001".asInt(), equalTo(9))
        assertThat("011001".asInt(), equalTo(25))
    }

    @Test
    fun `nth bit of an int`() {
        val eight = "001000"
        assertThat(eight.asInt().nthBit(0), equalTo(0))
        assertThat(eight.asInt().nthBit(1), equalTo(0))
        assertThat(eight.asInt().nthBit(2), equalTo(0))
        assertThat(eight.asInt().nthBit(3), equalTo(1))
        assertThat(eight.asInt().nthBit(4), equalTo(0))
        assertThat(eight.asInt().nthBit(5), equalTo(0))
    }

    @Test
    fun `most common byte in position 4 (zero indexed)`() {
        val expected = listOf(0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0)
        val actual = input.map { str -> str.asInt().nthBit(4) }
        assertThat(actual, equalTo(expected))
    }

    @Test
    fun `most common in list`() {
        assertThat(listOf(0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0).mostCommon(), equalTo(1))
        assertThat(listOf(0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0).mostCommon(), equalTo(0))
    }
}

fun String.asInt(): Int = this.reversed().foldIndexed(0) { idx, acc, bitStr ->
    acc + if (bitStr == '1') 2.0.pow(idx.toDouble()).toInt() else 0
}

fun Int.nthBit(n: Int): Int = (this shr n) and 1;

fun List<Int>.mostCommon(): Int {
    val foldResult = this.fold(0) { acc, num -> if (num == 1) acc + 1 else acc - 1 }
    return if (foldResult > 0) 1 else 0
}

fun List<Int>.leastCommon(): Int = if (this.mostCommon() == 1) 0 else 1

fun List<Int>.epsilon(): Int =
    (0..4)
        .reversed()
        .map { idx -> this.map { str -> str.nthBit(idx) }.leastCommon() }
        .joinToString(separator = "")
        .asInt()

fun List<Int>.gamma(): Int =
    (0..4)
        .reversed()
        .map { idx -> this.map { str -> str.nthBit(idx) }.mostCommon() }
        .joinToString(separator = "")
        .asInt()