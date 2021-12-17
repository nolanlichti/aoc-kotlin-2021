fun main() {
    debug.enabled = false

    fun part1(input: List<String>): Int {
        val dots = input.getDots()
        val folds = input.getFolds()

        return dots.foldIt(folds[0]).size
    }

    fun part2(input: List<String>) {
        val dots = input.getDots()
        val folds = input.getFolds()

        var currentDots = dots
        folds.forEach { fold ->
            currentDots = currentDots.foldIt(fold)
            println(currentDots)
            currentDots.print()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    val test1 = part1(testInput)
    val expected1 = 17
    check(test1 == expected1) { "Expected $expected1, was $test1" }

    val input = readInput("Day13")
    println()
    println("Part 1: ${part1(input)}")

//    val test2 = part2(testInput)
//    val expected2 = 0
//    check(test2 == expected2) { "Expected $expected2, was $test2" }
//    println("Part 2: ${part2(input)}")
    part2(input)
}

enum class Coordinate { X, Y }
data class Fold(val axis: Coordinate, val value: Int)

typealias Dot = MutableMap<Coordinate, Int>

fun List<Dot>.foldIt(fold: Fold): List<Dot> {
    val max = this.maxOf { it[fold.axis] ?: 0 }
    val result = this.toCollection(mutableListOf()).toList()
    result.filter { (it[fold.axis] ?: 0) > fold.value }
        .forEach { it[fold.axis] = max - it[fold.axis]!! }
    return result.distinct()
}

fun List<Dot>.print() {
    val maxX = this.maxOf { it[Coordinate.Y] ?: 0}
    val maxY = this.maxOf { it[Coordinate.X] ?: 0 }

    (0..maxY).forEach { y->
        (0..maxX).forEach { x ->
            print(if (this.any { it[Coordinate.X] == x && it[Coordinate.Y] == y }) "#" else ".")
        }
        println()
    }
}

val coordinatePattern = Regex("""^(\d+),(\d+)$""")
val instructionPattern = Regex("""^fold along ([xy])=(\d+)$""")

fun List<String>.getDots() = this.mapNotNull { coordinatePattern.matchEntire(it) }
    .map {
        mutableMapOf(
            Coordinate.X to it.destructured.component1().toInt(),
            Coordinate.Y to it.destructured.component2().toInt()
        )
    }

fun List<String>.getFolds() = this.mapNotNull { instructionPattern.matchEntire(it) }
    .map {
        Fold(
            axis = Coordinate.valueOf(it.destructured.component1().uppercase()),
            value = it.destructured.component2().toInt()
        )
    }
