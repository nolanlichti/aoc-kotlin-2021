import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int =
        input.map {
            val (x1, y1, x2, y2) = Regex("""(\d+),(\d+) -> (\d+),(\d+)""").matchEntire(it)!!.destructured
            Point(x1.toInt(), y1.toInt()) to Point(x2.toInt(), y2.toInt())
        }
            .filter { it.first.x == it.second.x || it.first.y == it.second.y }
            .map {
                val points = mutableListOf<Point>()
                if (it.first.x == it.second.x) {
                    val lowest = min(it.first.y, it.second.y)
                    val highest = max(it.first.y, it.second.y)
                    println("$lowest to $highest")
                    for (i in lowest..highest) {
                        points.add(Point(it.first.x, i))
                    }
                } else {
                    val lowest = min(it.first.x, it.second.x)
                    val highest = max(it.first.x, it.second.x)
                    for (i in lowest..highest) {
                        points.add(Point(i, it.first.y))
                    }
                }
                points
            }
            .flatten()
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .count()

    fun part2(input: List<String>): Int =
        input.map {
            val (x1, y1, x2, y2) = Regex("""(\d+),(\d+) -> (\d+),(\d+)""").matchEntire(it)!!.destructured
            Point(x1.toInt(), y1.toInt()) to Point(x2.toInt(), y2.toInt())
        }
            .map {
                val points = mutableListOf<Point>()
                if (it.first.x == it.second.x) {
                    val lowest = min(it.first.y, it.second.y)
                    val highest = max(it.first.y, it.second.y)
                    println("$lowest to $highest")
                    for (i in lowest..highest) {
                        points.add(Point(it.first.x, i))
                    }
                } else if (it.first.y == it.second.y){
                    val lowest = min(it.first.x, it.second.x)
                    val highest = max(it.first.x, it.second.x)
                    for (i in lowest..highest) {
                        points.add(Point(i, it.first.y))
                    }
                }
                points
            }
            .flatten()
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .count()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5) { "Expected 5, was ${part1(testInput)}" }

    val input = readInput("Day05")

    println(part1(input))

    check(part2(testInput) == null) { "Expected null, was ${part2(testInput)}" }
    println(part2(input))
}

data class Point(val x: Int, val y: Int)