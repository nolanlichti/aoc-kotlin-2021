import kotlin.math.ceil

fun main() {
    fun countItAll(input: List<String>, days: Int): Long {
        val fishes = input[0].split(",")
            .map { it.toInt() }
        val spawnByDay = mutableMapOf<Int, Long>()
        for (day in 0..8) {
            spawnByDay[day] = fishes.count { it == day }.toLong()
        }
        repeat(days) {
            val spawningFish = spawnByDay[0] ?: 0
            for (day in 0..7) {
                spawnByDay[day] = spawnByDay[day + 1] ?: 0
            }
            spawnByDay[8] = spawningFish
            spawnByDay[6] = (spawnByDay[6] ?: 0) + spawningFish
        }
        return spawnByDay.values.sum()
    }

    fun part1(input: List<String>): Long = countItAll(input, 80)

    fun part2(input: List<String>): Long = countItAll(input, 256)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val test1 = part1(testInput)
    check(test1 == 5934L) { "Expected 5934, was $test1" }

    val input = readInput("Day06")

    println(part1(input))

    val test2 = part2(testInput)
    check(test2 == 26984457539) { "Expected 26984457539, was $test2" }
    println(part2(input))
}
