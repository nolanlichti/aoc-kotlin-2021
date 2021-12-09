fun main() {

    fun String.sortCharacters() = this.toSortedSet().joinToString("")

    fun part1(input: List<String>): Int {

        return input.map { it.substringAfter(" | ").split(" ").filter(String::isNotBlank).map { str -> str.length } }
            .flatten()
            .count { listOf(2, 3, 4, 7).contains(it) }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(" | ") }
            .map {
                it[0].split(" ").map { str -> str.sortCharacters() } to it[1].split(" ")
                    .map { str -> str.sortCharacters() }
            }
            .sumOf { io ->
                val allCodes = io.first + io.second
                val one = allCodes.firstOrNull { it.length == 2 } ?: "1"
                val three = allCodes.firstOrNull { it.length == 5 && it.contains(one) } ?: "3"
                val four = allCodes.firstOrNull { it.length == 4 } ?: "4"
                val five = allCodes.firstOrNull { it.length == 5 && (it.toSortedSet() - four.toSortedSet()).size == 2 } ?: "5"
                val two = allCodes.firstOrNull { it.length == 5 && it != five } ?: "2"
                val six = allCodes.firstOrNull { it.length == 6 && (!it.contains(one[0]) || !it.contains(one[1])) } ?: "6"
                val seven = allCodes.firstOrNull { it.length == 3 } ?: "7"
                val eight = allCodes.firstOrNull { it.length == 7 } ?: "8"
                val nine = allCodes.firstOrNull { it.length == 6 && it != six && (it.toSortedSet() - four.toSortedSet()).size == 2 } ?: "9"
                val zero = allCodes.firstOrNull { it.length == 6 && it != six && it != nine } ?: "0"

                io.second.joinToString("") {
                    val number = when (it) {
                        one -> 1
                        two -> 2
                        three -> 3
                        four -> 4
                        five -> 5
                        six -> 6
                        seven -> 7
                        eight -> 8
                        nine -> 9
                        zero -> 0
                        else -> 0
                    }
                    number.toString()
                }.toInt()
            }

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val test1 = part1(testInput)
    val expected1 = 26
    check(test1 == expected1) { "Expected $expected1, was $test1" }

    val input = readInput("Day08")

    println("Part 1: ${part1(input)}")

    val test2 = part2(testInput)
    val expected2 = 61229
    check(test2 == expected2) { "Expected $expected2, was $test2" }
    println("Part 2: ${part2(input)}")
}
