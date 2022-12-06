fun main() {
    fun part1(input: List<String>): Int {
        var max = 0
        var p = 0

        input.forEach {
            val num = it.toIntOrNull()
            if (num == null) {
                if (p > max) {
                    max = p
                }
                p = 0
            } else {
                p += num
            }
        }
        return max
    }

    fun part2(input: List<String>): Int {
        val top = sortedSetOf<Int>()
        var p = 0

        input.forEach {
            val num = it.toIntOrNull()
            if (num == null) {

                top.add(p)
                p = 0
            } else {
                p += num
            }
        }
        return top.toList().takeLast(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
