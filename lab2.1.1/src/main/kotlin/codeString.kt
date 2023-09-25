/**
 * @author Natalia Nikonova
 */
private fun mapString(input: String, pair: RectanglePair, func: (Char, Char) -> Pair<Char, Char>): String {
    val result = mutableListOf<Char>()
    var i = 0
    while (i < input.length) {
        if (!pair.containsLetter(input[i])) {
            result.add(input[i])
            i -= 1
        } else {
            val firstLetter = input[i]
            var secondLetter: Char
            val buffer = mutableListOf<Char>()
            if (i + 1 == input.length) {
                secondLetter = pair.getAdditionalLetter()
            } else {
                secondLetter = input[i + 1]
                while (!pair.containsLetter(secondLetter)) {
                    buffer.add(secondLetter)
                    i++
                    secondLetter = if (i + 1 == input.length) {
                        pair.getAdditionalLetter()
                    } else {
                        input[i + 1]
                    }
                }
            }
            val resultPair = func(firstLetter, secondLetter)
            result.add(resultPair.first)
            if (buffer.isNotEmpty()) result.addAll(buffer)
            result.add(resultPair.second)
        }
        i += 2
    }
    return result.joinToString(separator = "")
}

fun encode(input: String, pair: RectanglePair): String {
    return mapString(input, pair) { leftLetter, rightLetter ->
        pair.encode(leftLetter, rightLetter)
    }
}

fun decode(input: String, pair: RectanglePair): String {
    return mapString(input, pair) { leftLetter, rightLetter ->
        pair.decode(leftLetter, rightLetter)
    }
}
