import java.util.Locale

/**
 * @author Natalia Nikonova
 */
fun main() {
    println("Введите текст для зашифровки в одну строку:")
    var input = readlnOrNull()
    while (input == null) {
        println("Вы ничего не ввели")
        input = readlnOrNull()
    }
    input = input.lowercase(Locale.getDefault())
    println("Для удобства привели текст к нижнему регистру:\n$input")
    val pair = RussianSquarePair()
    println("Используемые матрицы для шфирования:\n$pair")
    val resultEncode = encode(input, pair)
    println("Результат шифрования:\n$resultEncode")
    val resultDecode = decode(resultEncode, pair)
    println("Результат дешифрования:\n$resultDecode")
}
