import Line.*

private fun List<String>.isCommand(): Boolean = first() == "$"

fun main() {
    fun part1(input: List<String>): Int {
        return createTree(input)
            .getFolderSizes()
            .filter { it <= 100000 }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val day = "07"
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    println("part1_test=${part1(testInput)}")
    println("part2_test=${part2(testInput)}")

    val input = readInput("Day$day")
    println("part1=${part1(input)}")
    println("part2=${part2(input)}")
}

private fun createTree(input: List<String>): Node.Dir {
    val root: Node.Dir = Node.Root()
    var current: Node.Dir = root
    input.map { it.split(' ') }
        .map { it.parse() }
        .forEach { line ->
            when (line) {
                is CdRoot -> current = root
                is CdUp -> current = current.parent
                is Cd -> current = current.getDir(line.name)
                is Dir -> current.append(line)
                is File -> current.append(line)
                is Ls -> {}
                is Command -> {}
                is Output -> {}
            }
        }
    return root
}

private fun List<String>.parse() =
    when (get(0)) {
        "$" -> when (get(1)) {
            "ls" -> Ls
            "cd" -> when (get(2)) {
                "/" -> CdRoot
                ".." -> CdUp
                else -> Cd(get(2))
            }

            else -> throw IllegalArgumentException()
        }

        "dir" -> Dir(get(1))
        else -> File(get(0).toInt(), get(1))
    }

private sealed interface Line {
    interface Command : Line
    object Ls : Command
    object CdUp : Command
    object CdRoot : Command
    data class Cd(var name: String) : Command

    interface Output : Line
    data class Dir(val name: String) : Output
    data class File(val size: Int, val name: String) : Output
}

private sealed interface Node {
    val name: String
    val size: Int

    abstract class Dir : Node {
        abstract val parent: Dir

        private var content: List<Node> = emptyList()

        override val size: Int
            get() = content.sumOf { it.size }

        fun append(file: Line.File) {
            content += File(file.name, file.size)
        }

        fun append(dir: Line.Dir) {
            content += Sub(dir.name, this)
        }

        fun getDir(name: String) = content
            .filterIsInstance<Dir>()
            .first { it.name == name }

        fun getFolderSizes(): List<Int> =
            content
                .filterIsInstance<Dir>()
                .flatMap { it.getFolderSizes() } +
                    content.sumOf { it.size }

        fun getFoldersWithMaxSize(maxSize: Int): Int {
            val sub: Int = content
                .filterIsInstance<Dir>()
                .sumOf { it.getFoldersWithMaxSize(maxSize) }
            val thisSize = content.sumOf { it.size }

            return if (thisSize <= maxSize) {
                sub + thisSize
            } else {
                sub
            }
        }
    }

    data class Sub(override val name: String, override val parent: Dir) : Dir()
    class Root : Dir() {
        override val parent: Dir
            get() = throw IllegalArgumentException()

        override val name: String
            get() = "/"
    }

    data class File(override val name: String, override val size: Int) : Node

}