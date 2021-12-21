import kotlin.math.abs
import kotlin.system.measureTimeMillis

fun main() {
    debug.enabled = false

    fun getLeastRiskyPath(locations: List<Location>): Int {
        val startLocation = locations.first { it.x == 0 && it.y == 0 }
        val endLocation = locations.maxByOrNull { it.x + it.y }!!
        val riskForLocation = mutableMapOf(startLocation to 0)
        val locationsToVisit = mutableListOf(startLocation)

        while (!riskForLocation.containsKey(endLocation)) {
            val currentLocation = locationsToVisit.minByOrNull { riskForLocation[it]!! }!!
            val unvisitedLocations = locations - riskForLocation.keys
            val neighborLocations = unvisitedLocations.filter { it.isAdjacentTo(currentLocation) }

            neighborLocations.forEach { location ->
                val minRiskToLocation = riskForLocation[location]
                val newRiskToLocation = riskForLocation[currentLocation]!! + location.value
                if (minRiskToLocation == null || newRiskToLocation < minRiskToLocation) {
                    riskForLocation[location] = newRiskToLocation
                    val minRiskToEnd = riskForLocation[endLocation]
                    if (location == endLocation) {
                        println("New minimum risk is ${riskForLocation[location]}")
                    } else if (minRiskToEnd == null || newRiskToLocation + location.distanceTo(endLocation) < minRiskToEnd) {
                        locationsToVisit += location
                    }
                }
            }

            locationsToVisit -= currentLocation
        }

        return riskForLocation[endLocation]!!
    }

    fun part1(input: List<String>) = getLeastRiskyPath(input.getLocations())

    fun part2(input: List<String>): Int {
        val locationTile = input.getLocations()
        val locations = mutableListOf<Location>()
        val maxX = locationTile.maxOf { it.x }
        val maxY = locationTile.maxOf { it.y }
        for (x in 0..maxX) {
            for (y in 0..maxY) {
                val location = locationTile.first { it.x == x && it.y == x }
                for (xIncrement in 0..4) {
                    for (yIncrement in 0..4) {
                        var locationValue = location.value + xIncrement + yIncrement
                        while (locationValue > 9) {
                            locationValue -= 8
                        }
                        locations.add(Location(x = x + xIncrement * (maxX + 1), y = y + yIncrement * (maxY + 1), value = locationValue))
                    }
                }
            }
        }
        return getLeastRiskyPath(locations)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    val test1 = part1(testInput)
    val expected1 = 40
    check(test1 == expected1) { "Expected $expected1, was $test1" }

    val input = readInput("Day15")
    println("Part 1: ${part1(input)}")

    val test2 = part2(testInput)
    val expected2 = 315
    check(test2 == expected2) { "Expected $expected2, was $test2" }
    println("Part 2: ${part2(input)}")
}

fun Location.distanceTo(other: Location) = abs(this.x - other.x) + abs(this.y - other.y)
