import kotlin.math.abs

fun main() {
    fun List<Int>.getFuelCostOfPosition(position: Int): Int {
        return this.sumOf { abs(it - position) }
    }

    fun List<Int>.getFuelCostOfPositionCorrectly(position: Int): Int {
        return this.sumOf { (abs(it - position) + 1) * abs(it - position) / 2 }
    }

    fun List<String>.getCrabs() = this[0]
        .split(",")
        .filter(String::isNotBlank)
        .map(String::toInt)

    fun List<Int>.getBestPosition(fuelCostAlgo: List<Int>.(Int) -> Int) = (this.minOf { it }..this.maxOf { it }).toList()
        .minByOrNull { this.fuelCostAlgo(it) }!!

    fun part1(input: List<String>): Int {
        val crabs = input.getCrabs()

        val position = crabs.getBestPosition(List<Int>::getFuelCostOfPosition)

        return crabs.getFuelCostOfPosition(position)
    }

    fun part2(input: List<String>): Int {
        val crabs = input.getCrabs()
        val position = crabs.getBestPosition(List<Int>::getFuelCostOfPositionCorrectly)
        return crabs.getFuelCostOfPositionCorrectly(position)
    }
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val test1 = part1(testInput)
    val expected1 = 37
    check(test1 == expected1) { "Expected $expected1, was $test1" }

    val input = readInput("Day07")

    println("Part 1: ${part1(input)}")

    val test2 = part2(testInput)
    val expected2 = 168
    check(test2 == expected2) { "Expected $expected2, was $test2" }
    println("Part 2: ${part2(input)}")
}
