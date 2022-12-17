import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun Boolean.toInt(): Int = if (this) 1 else 0

fun List<Boolean>.sum(): Int = sumOf { it.toInt() }

fun List<String>.mapToInt() = map { it.toInt() }

fun <A, B, T> Pair<A, B>.mapFirst(transform: (A) -> T): Pair<T, B> = Pair(transform(first), second)

fun <E> List<E>.append(item: E): List<E> = listOf(this, listOf(item)).flatten()

fun <E> List<E>.appendAll(items: List<E>): List<E> =
    items.fold(this) { acc, item ->
        acc.append(item)
    }

fun List<Int>.multiply() = reduceOrNull { a, b -> a * b } ?: 0