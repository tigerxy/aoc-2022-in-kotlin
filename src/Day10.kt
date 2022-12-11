import Operation.Add
import Operation.Nop

fun main() {
    fun part1(input: List<String>): Int {
        val parsedInput = input.parse()
            .addHead(Add(1))
            .chunked(10)

        return (2..22 step 4)
            .sumOf {
                parsedInput
                    .take(it)
                    .flatten()
                    .sum() * it
            } * 10
    }

    fun part2(input: List<String>): String {
        return input.parse()
            .fold(State(1, emptyList())) { state, op ->
                with(state) {
                    val pos = crt.size % 40
                    val x = regX + op.value
                    val draw = (x - pos) in 0..2
                    copy(
                        regX = x,
                        crt = crt.append(draw)
                    )
                }
            }
            .crt
            .map { if (it) '#' else '.' }
            .chunked(40)
            .joinToString("\n") {
                it.joinToString("")
            }
    }

    val day = "10"

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val testOutput1 = part1(testInput)
    println("part1_test=$testOutput1")
    assert(testOutput1 == 13140)
    val testOutput2 = part2(testInput)
    println("part2_test=\n$testOutput2")

    val input = readInput("Day$day")
    println("part1=${part1(input)}")
    println("part2=\n${part2(input)}")
}

private fun <E> List<E>.append(item: E): List<E> =
    listOf(this, listOf(item)).flatten()

private fun List<Operation>.sum(): Int =
    sumOf { it.value }

private fun <E> List<E>.addHead(add: E): List<E> =
    listOf<List<E>>(listOf<E>(add), this).flatten()

private fun List<String>.parse(): List<Operation> =
    flatMap {
        if (it == "noop") {
            listOf(Nop)
        } else {
            val value = it.split(" ")[1].toInt()
            listOf(Nop, Add(value))
        }
    }

private sealed class Operation {
    open val value = 0

    object Nop : Operation()
    data class Add(override val value: Int) : Operation()
}

private data class State(val regX: Int, val crt: List<Boolean>)