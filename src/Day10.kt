fun main() {
    fun part1(input: List<String>): Int = input.sumOf { it.syntaxErrorScore() }


    fun part2(input: List<String>): Long {
        val scores = input.filter { it.isValid() }.map {
            val openChars = listOf('(', '[', '{', '<')
            val chunks = mutableListOf<Char>()
            for (c in it.toList()) {
                if (c in openChars) {
                    chunks += c
                } else {
                    chunks.removeLast()
                }
            }
            chunks.reversed().fold(0L) { score, c ->
                score.toLong() * 5L + when (c) {
                    '(' -> 1L
                    '[' -> 2L
                    '{' -> 3L
                    '<' -> 4L
                    else -> throw RuntimeException("unrecognized character $c")
                }
            }
        }.sorted()

        return scores[scores.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    val test1 = part1(testInput)
    val expected1 = 26397
    check(test1 == expected1) { "Expected $expected1, was $test1" }

    val input = readInput("Day10")

    println("Part 1: ${part1(input)}")

    val test2 = part2(testInput)
    val expected2 = 288957L
    check(test2 == expected2) { "Expected $expected2, was $test2" }
    println("Part 2: ${part2(input)}")
}

fun String.syntaxErrorScore(): Int {
    val openChars = mutableListOf<Char>()
    var result = 0
    for (c in this.toList()) {
        if (listOf('(', '[', '{', '<').contains(c)) {
            openChars += c
        } else {
            val validChar = when (c) {
                ')' -> '(' to 3
                ']' -> '[' to 57
                '}' -> '{' to 1197
                '>' -> '<' to 25137
                else -> throw RuntimeException("unrecognized character $c")
            }
            if (openChars.last() == validChar.first) {
                openChars.removeLast()
            } else {
                result = validChar.second
                break
            }
        }
    }
    return result
}

fun String.isValid() = this.syntaxErrorScore() == 0