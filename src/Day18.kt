fun main() {
    fun part1(input: List<String>): Int =
        (0..2)
            .flatMap { dimension ->
                input
                    .parse()
                    .groupBy { it.filterIndexed { idx, _ -> idx != dimension }.toPair() }
                    .map { it.value.map { it[dimension] }.sorted() }
            }
            .sumOf {
                if (it.size > 1) {
                    it.zipWithNext { a, b -> if (b - a > 1) 2 else 0 }.sum()
                } else {
                    0
                } + 2
            }

    fun part2(input: List<String>): Int = 0

    val day = "18"

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val testOutput1 = part1(testInput)
    println("part1_test=$testOutput1")
    assert(testOutput1 == 13)
    val testOutput2 = part2(testInput)
    println("part2_test=$testOutput2")
    assert(testOutput1 == 0)

    val input = readInput("Day$day")
    println("part1=${part1(input)}")
    println("part2=${part2(input)}")
}

private fun <E> List<E>.toPair() =
    Pair(first(), last())

private fun List<String>.parse(): List<List<Int>> =
    map { it.split(',').mapToInt() }