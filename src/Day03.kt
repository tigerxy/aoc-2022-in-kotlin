fun main() {
    fun part1(input: List<String>): Int {
        return input
            .mapNotNull { backpack ->
                val chars = backpack.toCharArray().toList()
                val compartments = chars.chunked(chars.size / 2)
                compartments.first().find { compartments.last().contains(it) }
            }
            .sumOf { it.toPriority() }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { backpack ->
                backpack.toCharArray().toList()
            }
            .chunked(3)
            .mapNotNull { elfs ->
                elfs[0].find {
                    elfs[1].contains(it) && elfs[2].contains(it)
                }
            }
            .sumOf { it.toPriority() }
    }

    val day = "03"

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val testOutput1 = part1(testInput)
    println("part1_test=$testOutput1")
    assert(testOutput1 == 13140)
    val testOutput2 = part2(testInput)
    println("part2_test=$testOutput2")
    assert(testOutput2 == 70)

    val input = readInput("Day$day")
    println("part1=${part1(input)}")
    println("part2=${part2(input)}")
}

private fun Char.toPriority() =
    when {
        code < 96 -> code - 38
        else -> code - 96
    }
