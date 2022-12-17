import TreeNode.ListNode

fun main() {
    fun part1(input: List<String>): Int =
        input
            .parseFile()
            .map { it.first < it.second }
            .mapIndexed { index, b ->
                b.toInt() * (index + 1)
            }
            .sum()

    fun part2(input: List<String>): Int = 0

    val day = "13"

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

private fun List<String>.parseFile(): List<Pair<TreeNode, TreeNode>> {
    return windowed(size = 2, step = 3)
        .map {
            it
                .map(::parse)
                .map(Pair<TreeNode, String>::first)
        }
        .map { Pair(it[0], it[1]) }
}

private fun parse(line: String): Pair<TreeNode, String> {
    val list = mutableListOf<TreeNode>()
    var left = line
    while (left.isNotEmpty()) {
        left = when (left.first()) {
            ']' -> {
                return Pair(ListNode(list), left.drop(1))
            }

            ',' -> {
                left.drop(1)
            }

            '[' -> {
                val res = parse(left.drop(1))
                list.add(res.first)
                res.second
            }

            else -> {
                val res = parseNumber(left)
                list.add(res.first)
                res.second
            }
        }
    }
    return Pair(ListNode(list), "")
}

private fun <A, B> Pair<A, B>.mapFirstIf(condition: (A) -> Boolean, transform: (A) -> A): Pair<A, B> =
    if (condition(first)) copy(first = transform(first)) else this

private fun <A, B> Pair<A, B>.mapSecondIf(condition: (B) -> Boolean, transform: (B) -> B): Pair<A, B> =
    if (condition(second)) copy(second = transform(second)) else this

private fun parseNumber(line: String): Pair<TreeNode, String> =
    Pair(
        line.takeWhile { it.isDigit() }.toInt().toNumberNode(),
        line.dropWhile { it.isDigit() }
    )

private fun Int.toNumberNode() = TreeNode.NumberNode(this)

private fun String.splitBefore(delimiter: Char): Pair<String, String> {
    val i = indexOf(delimiter)
    return Pair(substring(0, i), substring(i, lastIndex))
}

private sealed interface TreeNode {
    operator fun compareTo(other: TreeNode): Int =
        when {
            (this is NumberNode) && (other is NumberNode) ->
                this.value.compareTo(other.value)

            this is ListNode && other is ListNode ->
                this.compareToListNode(other)

            else ->
                this.toListNode().compareTo(other.toListNode())
        }

    fun toListNode(): ListNode

    data class NumberNode(val value: Int) : TreeNode {
        override fun toListNode() = ListNode(listOf(this))
    }

    data class ListNode(val value: List<TreeNode>) : TreeNode {
        override fun toListNode(): ListNode = this

        fun compareToListNode(other: ListNode): Int =
            value
                .zip(other.value) { a, b ->
                    a.compareTo(b)
                }
                .find { it != 0 }
                ?: value.size.compareTo(other.value.size)
    }
}
