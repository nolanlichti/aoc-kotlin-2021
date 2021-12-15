import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val octopuses = input.getOctopuses()

        octopuses.printGrid()

        return (1..100).sumOf { round ->
            debug.debugln("== $round ==")
            octopuses.next()
            octopuses.printGrid()
            octopuses.count { it.didFlash }
        }
    }

    fun part2(input: List<String>): Int {
        val octopuses = input.getOctopuses()
        var round = 0;
        debug.debugln("== $round ==")
        octopuses.printGrid()
        while (octopuses.any { it.didNotFlash }) {
            round += 1
            octopuses.next()
            debug.debugln("== $round ==")
            octopuses.printGrid()
        }
        return round
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    val test1 = part1(testInput)
    val expected1 = 1656
    check(test1 == expected1) { "Expected $expected1, was $test1" }

    val input = readInput("Day11")

    println("Part 1: ${part1(input)}")

    val test2 = part2(testInput)
    val expected2 = 195
    check(test2 == expected2) { "Expected $expected2, was $test2" }
    println("Part 2: ${part2(input)}")
}

data class Octopus(val x: Int, val y: Int, var energy: Int) {}

val Octopus.willFlash get() = this.energy > 9

val Octopus.didFlash get() = this.energy == 0

val Octopus.didNotFlash get() = !this.didFlash

fun Octopus.isAdjacentToAny(octopuses: List<Octopus>) = this.countAdjacentToAny(octopuses) > 0

fun Octopus.countAdjacentToAny(octopuses: List<Octopus>) =
    octopuses.count { (it.x != this.x || it.y != this.y) && abs(it.x - this.x) in 0..1 && abs(it.y - this.y) in 0..1 }

fun List<Octopus>.next() {
    this.forEach { it.energy += 1 }
    while (this.any { it.willFlash }) {
        val didFlash = this.filter { it.willFlash }
        didFlash.forEach { it.energy = 0 }
        this.filter { it.didNotFlash && it.isAdjacentToAny(didFlash) }
            .forEach { it.energy += it.countAdjacentToAny(didFlash) }
    }
}

fun List<Octopus>.printGrid() {
    val maxY = this.maxOf { it.y }
    (0..maxY).forEach { y ->
        this.filter { it.y == y }.sortedBy { it.x }.forEach { debug.debug(it.energy) }
        debug.debugln()
    }
}

fun List<String>.getOctopuses() = this.mapIndexed { row, octoRow ->
    octoRow.toList().mapIndexed { column, energy -> Octopus(x = column, y = row, energy = energy.digitToInt()) }
}.flatten()