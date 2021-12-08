import kotlin.math.abs

fun main() {
    fun List<Int>.getFuelCostOfPosition(position: Int): Int {
        return this.sumOf { abs(it - position) }
    }

    fun List<Int>.getFuelCostOfPositionCorrectly(position: Int): Int {
        return this.sumOf { (abs(it - position) + 1) * abs(it - position) / 2 }
    }

    fun List<Int>.getBestSum(positionsToCheck: List<Int>, fuelCostAlgo: List<Int>.(Int) -> Int): Int {
        println("checking $positionsToCheck")
        if (positionsToCheck.toSet().size == 1) {
            return positionsToCheck[0]
        }
        val currentBestPositionIndex = positionsToCheck.size / 2
        val currentBestPosition = positionsToCheck[currentBestPositionIndex]
        val currentBestSum = this.fuelCostAlgo(currentBestPosition)
        println("current best is $currentBestSum")
        val lowEnd = positionsToCheck.indexOfFirst { it == currentBestPosition }
        val lowerList = if (lowEnd > -1 ) positionsToCheck.subList(0, lowEnd) else listOf()
        val highStart = positionsToCheck.indexOfLast { it == currentBestPosition }
        val higherList = if (highStart < positionsToCheck.size) positionsToCheck.subList(highStart, positionsToCheck.size) else listOf()

        if (lowerList.size > 1) {
            val lowerBestSum = this.getBestSum(lowerList, fuelCostAlgo)
            println("lower set $lowerList has sum of $lowerBestSum")
            if (lowerBestSum < currentBestSum) {
                println("current best is now from lower set and is $lowerBestSum")
                return lowerBestSum
            }
        }

        if (higherList.size > 1) {
            val higherBestSum = this.getBestSum(higherList, fuelCostAlgo)
            println("higher set $higherList has sum of $higherBestSum")

            if (higherBestSum < currentBestSum) {
                println("current best is now from lower set and is $higherBestSum")
                return higherBestSum
            }
        }

        return currentBestSum
    }

    fun List<Int>.getBestSum(fuelCostAlgo: List<Int>.(Int) -> Int): Int = this.getBestSum(this, fuelCostAlgo)

    fun part1(input: List<String>): Int = input[0]
        .split(",")
        .filter(String::isNotBlank)
        .map(String::toInt)
        .sorted()
        .getBestSum(List<Int>::getFuelCostOfPosition)

    fun part2(input: List<String>): Int = input[0]
        .split(",")
        .filter(String::isNotBlank)
        .map(String::toInt)
        .sorted()
        .getBestSum(List<Int>::getFuelCostOfPositionCorrectly)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val test1 = part1(testInput)
    val expected1 = 37
    check(test1 == expected1) { "Expected $expected1, was $test1" }

    val input = readInput("Day07")

    println("SOLUTION: ${part1(input)}")

    val test2 = part2(testInput)
    val expected2 = 168
    check(test2 == expected2) { "Expected $expected2, was $test2" }
    println(part2(input))
}
