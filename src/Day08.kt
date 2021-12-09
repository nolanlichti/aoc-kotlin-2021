fun main() {

    fun part1(input: List<String>): Int {

        return input.map { it.substringAfter(" | ").split(" ").filter(String::isNotBlank).map { str -> str.length } }
            .flatten()
            .count { listOf(2, 3, 4, 7).contains(it) }
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(" | ") }
            .map {
                it[0].split(" ").map { str -> str.toSortedSet() } to it[1].split(" ")
                    .map { str -> str.toSortedSet() }
            }
            .sumOf { io ->
                val one = io.first.first { it.size == 2 }
                val three = io.first.first { it.size == 5 && it.containsAll(one) }
                val four = io.first.first { it.size == 4 }
                val five = io.first.first { it.size == 5 && it != three && (it - four).size == 2 }
                val two = io.first.first { it.size == 5  && it != three && it != five }
                val six = io.first.first { it.size == 6 && !it.containsAll(one) }
                val seven = io.first.first { it.size == 3 }
                val eight = io.first.first { it.size == 7 }
                val nine = io.first.first { it.size == 6 && it != six && (it - four).size == 2 }
                val zero = io.first.first { it.size == 6 && it != six && it != nine }

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
