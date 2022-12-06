fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { round ->
            val other = round.first().map()
            val me = round.last().map()

            val result = when {
                other == me -> 3
                me == Rock && other == Scissors -> 6
                me == Scissors && other == Paper -> 6
                me == Paper && other == Rock -> 6
                else -> 0
            }

            me + result
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { round ->
            val other = round.first().map()
            val action = round.last().map()

            val result = when {
                action == DRAW -> other
                other == Scissors && action == LOSE -> Paper
                other == Paper && action == LOSE -> Rock
                other == Rock && action == LOSE -> Scissors
                other == Scissors && action == WIN -> Rock
                other == Paper && action == WIN -> Scissors
                other == Rock && action == WIN -> Paper
                else -> 0
            }

            result + (action - 1) * 3
        }
    }

    val day = "02"
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    println("part1_test=${part1(testInput)}")
    println("part2_test=${part2(testInput)}")

    val input = readInput("Day$day")
    println("part1=${part1(input)}")
    println("part2=${part2(input)}")
}

private const val Rock = 1
private const val Paper = 2
private const val Scissors = 3

private const val LOSE = 1
private const val DRAW = 2
private const val WIN = 3

private fun Char.map() = when (this) {
    'A' -> Rock
    'B' -> Paper
    'C' -> Scissors
    'X' -> Rock
    'Y' -> Paper
    'Z' -> Scissors
    else -> 0
}
