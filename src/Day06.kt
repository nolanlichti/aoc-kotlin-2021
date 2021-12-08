fun main() {
    fun part1(input: List<String>): Long {
        val fishes = input[0].split(",")
            .map { it.toInt() }
    for (i in 0..18) {
        println("day $i: ${fishes
        .map { (i - it).countAncestors() }
            .sumOf {it} + fishes.size} ")
    }
        return fishes.size.toLong()
    }
    fun part2(input: List<String>): Long = input.size.toLong()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L) { "Expected 5934, was ${part1(testInput)}" }

    val input = readInput("Day05")

    println(part1(input))

    check(part2(testInput) == 26984457539) { "Expected 26984457539, was ${part2(testInput)}" }
    println(part2(input))
}

fun Int.countAncestors(): Long {
    val children = if (this > 0) this / 7 + 1 else 0
    //println("children is $children for $this")
    var nextBirthday = this - 9
    var count = children.toLong()
    while (nextBirthday > -1) {
        count += nextBirthday.countAncestors()
        nextBirthday -= 9
    }
    return count
}