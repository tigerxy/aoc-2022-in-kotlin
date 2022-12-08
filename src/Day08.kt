fun main() {
    fun part1(input: List<String>): Int {
        return with(input.parse()) {
            isVerticalVisible()
                .or(isHorizontalVisible())
                .sum()
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val day = "08"

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val testOutput1 = part1(testInput)
    println("part1_test=$testOutput1")
    assert(testOutput1 == 21)
    val testOutput2 = part2(testInput)
    println("part2_test=$testOutput2")
    assert(testOutput2 == 0)

    val input = readInput("Day$day")
    println("part1=${part1(input)}")
    println("part2=${part2(input)}")
}

private fun List<List<Boolean>>.sum() =
    sumOf { line ->
        line.sumOf { cell ->
            cell.toInt()
        }
    }

private infix fun List<List<Boolean>>.or(other: List<List<Boolean>>): List<List<Boolean>> =
    mapIndexed { x, line ->
        line.mapIndexed { y, cell ->
            cell || other[x][y]
        }
    }

private fun List<List<Int>>.isVerticalVisible() =
    rotate().isHorizontalVisible().rotate()

private fun <E> List<List<E>>.rotate(): List<List<E>> =
    List(first().size) { x ->
        List(size) { y ->
            this[y][x]
        }
    }

private fun Boolean.toInt(): Int = if (this) 1 else 0

private fun List<List<Int>>.isHorizontalVisible(): List<List<Boolean>> =
    map { it.visible() }

private fun List<Int>.visible(): List<Boolean> {
    var p1 = 0
    var p2 = lastIndex
    var leftMax = this[p1]
    var rightMax = this[p2]
    val visible = List(size) { false }.toMutableList()
    visible[p1] = true
    visible[p2] = true

    while (p1 != p2) {
        if (leftMax < rightMax) {
            p1++
            if (this[p1] > leftMax) {
                leftMax = this[p1]
                visible[p1] = true
            }
        } else {
            p2--
            if (this[p2] > rightMax) {
                rightMax = this[p2]
                visible[p2] = true
            }
        }
    }
    return visible.toList()
}

private fun List<String>.parse() =
    map { line ->
        line.toCharArray()
            .map { it.digitToInt() }
    }
