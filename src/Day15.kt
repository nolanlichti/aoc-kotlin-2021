import kotlin.math.abs

fun main() {
    debug.enabled = false

    fun getLeastRiskyPath(locations: List<Location>): Int {
        val locationLookup = locations.associateBy { "${it.x}-${it.y}" }
        val startLocation = locationLookup["0-0"]
        val endLocation = locations.maxByOrNull { it.x + it.y }!!
        val riskForLocation = mutableMapOf(startLocation to 0)
        val locationsToVisit = mutableListOf(startLocation)

        while (!riskForLocation.containsKey(endLocation)) {
            val currentLocation = locationsToVisit.minByOrNull { riskForLocation[it]!! }!!
            val neighborLocations = locationLookup.getNeighborsForLocation(currentLocation)

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
        val maxTileX = locationTile.maxOf { it.x }
        val maxTileY = locationTile.maxOf { it.y }

        for (templateX in 0..maxTileX) {
            for (templateY in 0..maxTileY) {
                val location = locationTile.first { it.x == templateX && it.y == templateY }
                for (xShift in 0..4) {
                    for (yShift in 0..4) {
                        var locationValue = location.value + xShift + yShift
                        while (locationValue > 9) {
                            locationValue -= 9
                        }
                        val xToModify = templateY + yShift * (maxTileY + 1)
                        val yToModify = templateX + xShift * (maxTileX + 1)
                        locations.add(Location(x = xToModify, y = yToModify, value = locationValue))
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

typealias LocationLookup = Map<String, Location>

fun Location.distanceTo(other: Location) = abs(this.x - other.x) + abs(this.y - other.y)

fun LocationLookup.getNeighborsForLocation(location: Location) = listOf(
    this["${location.x - 1}-${location.y}"],
    this["${location.x + 1}-${location.y}"],
    this["${location.x}-${location.y + 1}"],
    this["${location.x}-${location.y - 1}"]
).filterNotNull()