fun main() {
    debug.enabled = false
    fun part1(input: List<String>): Int {
        val coordinatePattern = Regex("""(\d+),(\d+)""")
        val instructionPattern = Regex("""fold along ([xy])=(\d+)""")
        val dots = input.mapNotNull { coordinatePattern.matchEntire(it) }
            .map { Dot(x = it.destructured.component1().toInt(), y = it.destructured.component2().toInt()) }
        val folds = input.mapNotNull { instructionPattern.matchEntire(it) }
            .map { Fold(axis = it.destructured.component1(), value = it.destructured.component2().toInt()) }



        return dots.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    val test1 = part1(testInput)
    val expected1 = 17
    check(test1 == expected1) { "Expected $expected1, was $test1" }

    val input = readInput("Day13")
    println()
    println("Part 1: ${part1(input)}")

    val test2 = part2(testInput)
    val expected2 = 0
    check(test2 == expected2) { "Expected $expected2, was $test2" }
    println("Part 2: ${part2(input)}")
}

data class Dot(val x: Int, val y: Int)
data class Fold(val axis: String, val value: Int)

fun List<Dot>.fold(fold: Fold): List<Dot> {
    val predicate =
    val half = this.filter {  }
}