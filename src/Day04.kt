fun main() {
    fun part1(input: List<String>): Int =
        input
            .parse()
            .map { it.fullyContainsOther() }
            .sum()

    fun part2(input: List<String>): Int =
        input
            .parse()
            .map { it.overlapsOther() }
            .sum()

    val day = "04"

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

private fun Pair<IntRange,IntRange>.overlapsOther() =
    first.contains(second.first) || first.contains(second.last) || second.contains(first.first) || second.contains(first.last)

private fun Pair<IntRange,IntRange>.fullyContainsOther() =
    first.inside(second) || second.inside(first)

private fun IntRange.inside(other: IntRange): Boolean =
    first <= other.first && other.last <= last

private fun List<String>.parse(): List<Pair<IntRange, IntRange>> =
    map { line ->
        line.split(',', '-')
            .asSequence()
            .map { it.toInt() }
            .chunked(2)
            .map { IntRange(it[0], it[1]) }
            .chunked(2)
            .map { Pair(it[0], it[1]) }
            .first()
    }
