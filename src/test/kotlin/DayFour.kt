import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import kotlin.properties.Delegates

class DayFour {
    private val unexpectedBoardWinner = { _: Int -> fail { "unexpected win" } }
    private val input = listOf(
        7, 4, 9, 5, 11, 17, 23, 2, 0, 14, 21, 24, 10, 16, 13, 6, 15, 25, 12, 22, 18, 20, 8, 19, 3, 26, 1
    )
    private val nums1 = listOf(
        22, 13, 17, 11, 0,
        8, 2, 23, 4, 24,
        21, 9, 14, 16, 7,
        6, 10, 3, 18, 5,
        1, 12, 20, 15, 19
    )

    private val nums2 = listOf(
        3, 15, 0, 2, 22,
        9, 18, 13, 17, 5,
        19, 8, 7, 25, 23,
        20, 11, 10, 24, 4,
        14, 21, 16, 12, 6,
    )

    private val nums3 = listOf(
        14, 21, 17, 24, 4,
        10, 16, 15, 9, 19,
        18, 8, 23, 26, 20,
        22, 11, 13, 6, 5,
        2, 0, 12, 3, 7
    )

    @Test
    fun `the winner is board 3`() {
        var idx = 0
        var drawnNumber: Int
        var winnerFound = false

        val board1 = Board(nums1, listOf(unexpectedBoardWinner))
        val board2 = Board(nums2, listOf(unexpectedBoardWinner))
        val board3 = Board(
            nums3, listOf(
                { assertThat(it, equalTo(4512)) },
                { winnerFound = true })
        )

        do {
            drawnNumber = input[idx++]
            board1.mark(drawnNumber)
            board2.mark(drawnNumber)
            board3.mark(drawnNumber)
        } while (!winnerFound)

        assertThat(drawnNumber, equalTo(24))
    }
}

data class Board(var numbers: List<Int>, val observers: List<(Int) -> Unit> = listOf()) {
    fun mark(drawnNumber: Int) {
        numbers = numbers.map { if (it == drawnNumber) -1 else it }
        if (isWinner()) winningScore = (numbers.filter { it != -1 }.sum()) * drawnNumber
    }

    private fun isWinner() = (0..4).fold(false) { acc, idx ->
        acc || numbers.row(idx).isWinner() || numbers.col(idx).isWinner()
    }

    private var winningScore: Int by Delegates.observable(0) { _, _, newValue ->
        observers.forEach { it(newValue) }
    }
}

fun List<Int>.isWinner(): Boolean = this.all { it == -1 }
fun List<Int>.row(rowIdx: Int): List<Int> = (0..4).map { this[it + (rowIdx * 5)] }
fun List<Int>.col(colIdx: Int): List<Int> = (0..4).map { this[colIdx + (it * 5)] }
