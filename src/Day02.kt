fun main() {
    val mapToCommand = { input: String ->
        Regex("""([a-z]+) (\d+)""").matchEntire(input)!!.destructured.let { (command, magnitude) ->
            Command(
                command,
                magnitude.toInt()
            )
        }
    }

    fun part1(input: List<String>): Int = input.map(mapToCommand)
        .fold(Position(0, 0)) { position, command ->
            val (directive, magnitude) = command
            val distance = if (directive == "up") -1 * magnitude else magnitude
            if (directive == "forward") {
                position.copy(distance = position.distance + distance)
            } else {
                position.copy(depth = position.depth + distance)
            }
        }
        .let { it.depth * it.distance }

    fun part2(input: List<String>): Int = input.map(mapToCommand)
        .fold(Position(0, 0)) { position, command ->
            val (directive, magnitude) = command
            if (directive == "forward") {
                position.copy(
                    depth = position.depth + magnitude * position.heading,
                    distance = position.distance + magnitude
                )
            } else {
                val delta = if (directive == "down") magnitude else -1 * magnitude
                position.copy(heading = position.heading + delta)
            }
        }
        .let { it.depth * it.distance }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150) { "Expected 150, was ${part1(testInput)}" }
    check(part2(testInput) == 900) { "Expected 900, was ${part2(testInput)}" }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

data class Command(val directive: String, val magnitude: Int)
data class Position(val depth: Int, val distance: Int, val heading: Int = 0)