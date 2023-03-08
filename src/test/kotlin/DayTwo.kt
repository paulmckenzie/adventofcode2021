import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import kotlin.system.exitProcess

class DayTwo {
    @Test
    fun `plotting a route`() {
        val input = listOf(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2"
        )

        val (depth, track) = input.fold(Position()) { position, plot -> position.add(plot.toPositionDelta()) }

        assertThat(depth * track, equalTo(150))
    }
}

data class Position(val depth: Int = 0, val track: Int = 0)

fun Position.add(delta: Position): Position = Position(this.depth + delta.depth, this.track + delta.track)

fun String.toPositionDelta(): Position {
    val strings = this.split(" ")
    val direction: String = strings[0]
    val change: Int = strings[1].toInt()

    return when (direction) {
        "forward" -> Position(track = change)
        "back" -> Position(track = -change)
        "up" -> Position(depth = -change)
        "down" -> Position(depth = change)
        else -> exitProcess(1)
    }
}

