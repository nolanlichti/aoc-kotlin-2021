import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val heightMap = input.toHeightMap()
        return heightMap.map { location ->
            if (heightMap.filter { it.isAdjacentTo(location) }
                    .all { location.height < it.height }) location.height + 1 else 0
        }
            .sumOf { it }
    }

    fun part2(input: List<String>): Int {
        val mutableHeightMap = input.toHeightMap().toMutableList()
        val basins = mutableListOf<MutableList<Location>>()
        while (mutableHeightMap.any { it.height != 9 }) {
            val location = mutableHeightMap.first { it.height != 9 }
            mutableHeightMap -= location
            val basin = basins.firstOrNull { it.hasLocationAdjacentTo(location) } ?: mutableListOf()
            if (basin.isEmpty()) {
                basins.add(basin)
            }
            basin.add(location)
        }

        val combinedBasins = mutableListOf<MutableList<Location>>()
        combinedBasins += basins.removeFirst()
        while (basins.isNotEmpty()) {
            val basinToCheck = basins.removeFirst()
            val basinToCombine =
                combinedBasins.firstOrNull { basin -> basin.any { basinToCheck.hasLocationAdjacentTo(it) } }
            if (basinToCombine == null) {
                combinedBasins += basinToCheck
            } else {
                basinToCombine += basinToCheck
            }
        }

        return combinedBasins.map { it.size }.sortedDescending().subList(0, 3).reduce { a, b -> a * b }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val test1 = part1(testInput)
    val expected1 = 15
    check(test1 == expected1) { "Expected $expected1, was $test1" }

    val input = readInput("Day09")

    println("Part 1: ${part1(input)}")

    val test2 = part2(testInput)
    val expected2 = 1134
    check(test2 == expected2) { "Expected $expected2, was $test2" }
    println("Part 2: ${part2(input)}")
}

data class Location(val x: Int, val y: Int, val height: Int)

fun List<String>.toHeightMap(): List<Location> =
    this.map { it.toList().map { c -> c.digitToInt() } }
        .mapIndexed { row, heights ->
            heights.mapIndexed { column, height -> Location(x = row, y = column, height = height) }
        }
        .flatten()

fun Location.isAdjacentTo(other: Location) = (abs(this.x - other.x) == 1 && this.y == other.y)
        || (abs(this.y - other.y) == 1 && this.x == other.x)

fun Collection<Location>.hasLocationAdjacentTo(location: Location) =
    this.any { it.isAdjacentTo(location) }
