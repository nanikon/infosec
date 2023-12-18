import java.util.Collections
import kotlin.random.Random


/**
 * @author Natalia Nikonova
 */
val maxValueInt = UInt.MAX_VALUE.toLong()
val k = Array(8) { longToBinary(Random.nextLong(0, maxValueInt), 32) }.asList()
val x = listOf(k, k, k, k.asReversed()).flatten()
val s = Array (16) { (0..15).map { longToBinary(it.toLong(), 4) }.shuffled() }

fun main() {
    println("Введите текст для зашифровки в одну строку (можно использовать только символы ascii):")
    var input = readlnOrNull()
    while (input == null) {
        println("Вы ничего не ввели")
        input = readlnOrNull()
    }

    val blocks = convertToBinary(input).chunked(64)
    var result = ""

    for (block in blocks) {
        result += coding(block)
    }

    val textResult = result.chunked(16).map { it.toInt(2).toChar() }.joinToString("")
    println(textResult)
    println(result)


}

fun coding(input: String): String {
    val midList = input.chunked(32)
    var a = midList[0]
    var b = midList[1]
    for (i in 0..31) {
        val tmp = a
        a = xor(b, f(a, x[i]))
        b = tmp
    }
    return a + b
}

fun f(a: String, xI: String): String {
    val a1 = a.toLong(2)
    val xI1 = xI.toLong(2)
    val result1 = (a1 + xI1) % maxValueInt
    val result = longToBinary(result1, 32)
        .chunked(4)
        .mapIndexed { index, str -> s[index][str.toInt(2)] }
    Collections.rotate(result, 21)
    return result.joinToString("")
}

fun xor(a: String, b: String): String {
    val a1 = a.toLong(2)
    val b1 = b.toLong(2)
    val result = a1 xor b1
    return longToBinary(result,32)
}

fun convertToBinary(input: String): String {
    var binary = ""

    for (char in input) {
        binary += charToBinary(char)
    }

    while (binary.length % 64 != 0) {
        binary += charToBinary(' ')
    }

    return binary
}

fun longToBinary(int: Long, len: Int): String = java.lang.Long.toBinaryString(int).padStart(len, '0')
fun charToBinary(char: Char): String = longToBinary(char.code.toLong(), 16)
