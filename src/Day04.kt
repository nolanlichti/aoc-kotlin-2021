fun main() {
    fun part1(input: List<String>): Int {
        val numbersToCall = input.getNumbersToCall()

        val boards = input.getBoards()

        boards.callNumbers(numbersToCall) { it.any { board -> board.isWinner } }

        return boards.first { it.isWinner }.getScore()
    }

    fun part2(input: List<String>): Int {
        val numbersToCall = input.getNumbersToCall()

        val boards = input.getBoards()

        boards.callNumbers(numbersToCall) { it.all { board -> board.isWinner } }

        return boards.first { it.place == boards.size }.getScore()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512) { "Expected 4512, was ${part1(testInput)}" }

    val input = readInput("Day04")

    println(part1(input))

    check(part2(testInput) == 1924) { "Expected 1924, was ${part2(testInput)}" }
    println(part2(input))
}

data class BingoBoard(
    val squares: List<BingoSquare>,
    var isWinner: Boolean = false,
    var winningNumber: Int? = null,
    var place: Int? = null
)

fun BingoBoard.checkForWin(): Boolean {
    for (lines in 0..4) {
        if (this.squares.filter { it.row == lines && it.marked }.size == 5
            || this.squares.filter { it.column == lines && it.marked }.size == 5
        ) {
            return true
        }
    }
    return false
}

fun List<String>.getNumbersToCall() = this[0].split(",").map { it.toInt() }
fun List<String>.getBoards() = this.subList(1, this.size)
    .filter { it.isNotBlank() }
    .map { it.split(" ").filter(String::isNotBlank) }
    .flatten()
    .map { it.toInt() }
    .chunked(25)
    .map {
        it.mapIndexed { index, number ->
            BingoSquare(
                row = index % 5,
                column = index / 5,
                number = number
            )
        }
    }
    .map { BingoBoard(squares = it) }

fun BingoBoard.getScore(): Int =
    this.squares.filter { !it.marked }.sumOf { it.number } * (this.winningNumber ?: 0)

fun List<BingoBoard>.callNumbers(numbers: List<Int>, predicate: (boards: List<BingoBoard>) -> Boolean): Unit {
    for (number in numbers) {
        this.filter { !it.isWinner }
            .forEach { board ->
                board.squares.find { it.number == number }?.marked = true
                board.isWinner = board.checkForWin()
                if (board.isWinner) {
                    board.winningNumber = number
                    board.place = this.maxOf { it.place ?: 0 } + 1
                }
            }

        if (predicate.invoke(this)) {
            break
        }
    }
}

data class BingoSquare(
    val row: Int,
    val column: Int,
    val number: Int,
    var marked: Boolean = false
)