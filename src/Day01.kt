fun main() {
    fun increasedDepthCounter(readings: List<String>, readingDifference: Int) = { index: Int, count: Int, num: Int ->
        if (index > readingDifference - 1 && num > readings[index - readingDifference].toInt()) count + 1 else count
    }

    fun part1(input: List<String>): Int = input.map { it.toInt() }
        .foldIndexed(0, increasedDepthCounter(input, 1))

    fun part2(input: List<String>): Int = input.map { it.toInt() }
        .foldIndexed(0, increasedDepthCounter(input, 3))

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7) { "Expected 7, was ${part1(testInput)}" }
    check(part2(testInput) == 5) { "Expected 5, was ${part2(testInput)}" }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
