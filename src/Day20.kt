fun main() {
    fun part1(input: List<String>): Int {
        val result = input
            .parse()
            .scramble()

        return (1000..3000 step 1000)
            .map { it + result.indexOf(0) }
            .sumOf { result.getMod(it) }
    }

    fun part2(input: List<String>): Int = 0

    val day = "20"

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val testOutput1 = part1(testInput)
    println("part1_test=$testOutput1")
    assert(testOutput1 == 3)
    val testOutput2 = part2(testInput)
    println("part2_test=$testOutput2")
    assert(testOutput1 == 0)

    val input = readInput("Day$day")
    println("part1=${part1(input)}")
    println("part2=${part2(input)}")
}

private fun List<Int>.scramble(): List<Int> =
    withIndex()
        .fold(withIndex()) { list, e ->
            val oldIdx = list.indexOfFirst { it.index == e.index }
            var newIdx = (oldIdx + e.value) % lastIndex
            newIdx = if (newIdx <= 0) lastIndex + newIdx else newIdx
            val newList = list.toMutableList()
            newList.removeAt(oldIdx)
            newList.add(newIdx, e)
            newList
        }
        .map { it.value }

private fun List<String>.parse() = mapToInt()
private fun List<Int>.getMod(n: Int) = get((n % size))
private fun sum(vararg int: Int) = int.sum()