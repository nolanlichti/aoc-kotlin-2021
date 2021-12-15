fun main() {
    debug.enabled = true
    fun part1(input: List<String>): Int {
        val caveLinks = input.getCaves()
        val end = caveLinks.flatMap { it.toList() }.first { it.isEnd }
        return end.getCountOfIncomingPaths(caveLinks = caveLinks)
    }

    fun part2(input: List<String>): Int {
        val caveLinks = input.getCaves()
        val end = caveLinks.flatMap { it.toList() }.first { it.isEnd }
        return end.getCountOfIncomingPaths(caveLinks = caveLinks, maxOccurrencesOfOneCave = 2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
//    val test1 = part1(testInput)
//    val expected1 = 19
//    check(test1 == expected1) { "Expected $expected1, was $test1" }

    val input = readInput("Day12")
//    println()
//    println("Part 1: ${part1(input)}")

    val test2 = part2(testInput)
    val expected2 = 103
    check(test2 == expected2) { "Expected $expected2, was $test2" }
    println("Part 2: ${part2(input)}")
}

data class Cave(val name: String)

fun Cave.getLinkedCaves(caveLinks: List<Pair<Cave, Cave>>, excludedCaves: List<Cave> = listOf()) =
    caveLinks.filter { it.first == this || it.second == this }.map { it.toList() }.flatten().filter { it != this }
        .minus(excludedCaves.toSet())

fun Cave.hasName(name: String) = this.name == name
val Cave.isStart get() = this.hasName("start")
val Cave.isEnd get() = this.hasName("end")
val Cave.isSmallCave get() = Regex("^[a-z]+$").matches(this.name)

fun Cave.getCountOfIncomingPaths(
    caveLinks: List<Pair<Cave, Cave>>,
    visitedCaves: List<Cave> = listOf(),
    maxOccurrencesOfOneCave: Int = 1,
    depth: Int = 0
): Int {
    repeat(depth) { debug.debug("\t") }
    debug.debug("Cave ${this.name}: ")
    if (this.isStart) {
        debug.debugln("Path to start is ${visitedCaves.joinToString("->") { it.name }}")
        return 1
    }
    val shouldNotExcludeCaves = visitedCaves.doesNotHaveMaxOccurrencesOfAnyCave(maxOccurrencesOfOneCave)
    val linkedCaves =
        this.getLinkedCaves(caveLinks, if (shouldNotExcludeCaves) listOf() else visitedCaves).filter { !it.isEnd }
    debug.debug("linked caves are ${linkedCaves.map { it.name }} ")
    val excludedCaves = visitedCaves.toMutableList()
    if (this.isSmallCave && (!visitedCaves.contains(this) || shouldNotExcludeCaves)) {
        excludedCaves += this
    }
    debug.debugln("(${excludedCaves.map { it.name }} will be excluded)")
    return linkedCaves.sumOf {
        it.getCountOfIncomingPaths(
            caveLinks, excludedCaves, maxOccurrencesOfOneCave = maxOccurrencesOfOneCave, depth = depth + 1
        )
    }
}

fun List<Cave>.doesNotHaveMaxOccurrencesOfAnyCave(maxOccurrences: Int) =
    this.groupingBy { it.name }.eachCount().values.none { it == maxOccurrences }

fun List<String>.getCaves() = this.map { it.split("-").map { str -> Cave(str) } }.map { it[0] to it[1] }