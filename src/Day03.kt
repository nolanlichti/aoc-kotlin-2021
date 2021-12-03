fun main() {
    fun part1(input: List<String>): Int {
        val gamma = input.map { it.toList().map { c -> c.digitToInt() } }
            .reduce { acc, list -> acc.mapIndexed { index, i -> list[index] + i } }
            .joinToString("") { if (it > input.size / 2) "1" else "0" }
            .toInt(2)

        val epsilon = gamma.toString(2)
            .padStart(input[0].length, '0')
            .map { if (it == '1') 0 else 1 }
            .joinToString("")
            .toInt(2)
        println("$gamma * $epsilon = ${gamma * epsilon}")
        return gamma * epsilon
    }

    fun part2(input: List<String>): Int {
        val listsOfInts = input.map { it.toList().map { c -> c.digitToInt() } }
        val mutableList = listsOfInts.toMutableList()
        var index = 0
        while (mutableList.size > 1) {
            mutableList.removeIf {
                it[index] != if (mutableList.sumOf { it[index] }
                        .toDouble() < mutableList.size.toDouble() / 2.0) 0 else 1
            }
            index += 1
        }

        val oxygenGeneratorRating = mutableList.first().joinToString("").toInt(2)
        val mutableList2 = listsOfInts.toMutableList()
        index = 0
        while (mutableList2.size > 1) {
            val bit = if (mutableList2.sumOf { it[index] }.toDouble() < mutableList2.size.toDouble() / 2.0) 1 else 0
            mutableList2.removeIf { it[index] != bit }
            index += 1
        }
        val co2ScrubberRating = mutableList2.first().joinToString("").toInt(2)

        println("$oxygenGeneratorRating * $co2ScrubberRating = ${oxygenGeneratorRating * co2ScrubberRating}")
        return oxygenGeneratorRating * co2ScrubberRating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198) { "Expected 198, was ${part1(testInput)}" }

    val input = readInput("Day03")

    println(part1(input))

    check(part2(testInput) == 230) { "Expected 230, was ${part2(testInput)}" }
    println(part2(input))
}
