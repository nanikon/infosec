/**
 * @author Natalia Nikonova
 */
abstract class RectanglePair(
   val width: Int,
   val height: Int,
   val letterString: String
) {
   private var leftMatrix: Array<Array<Char>>
   private var rightMatrix: Array<Array<Char>>
   private var letterSet: Set<Char>
   private val leftCache = mutableMapOf<Char, Pair<Int, Int>>()
   private val rightCache = mutableMapOf<Char, Pair<Int, Int>>()

   init {
      if (width <= 0)
         throw IllegalArgumentException("Can't use square with non positive square")
      if (height <= 0)
         throw IllegalArgumentException("Can't use square with non positive height")
      if (width * height != letterString.length)
         throw IllegalArgumentException("Can't use square with non equal letters and cells")

      val alreadyUsed = mutableSetOf<Char>()
      letterSet = letterString.toCharArray().toSet()
      leftMatrix = Array(height) { y ->
         Array(width) { x ->
            letterSet.minus(alreadyUsed).random().also {
               alreadyUsed.add(it)
               leftCache[it] = Pair(y, x)
            }
         }
      }
      alreadyUsed.clear()
      rightMatrix = Array(height) { y ->
         Array(width) { x ->
            letterSet.minus(alreadyUsed).random().also {
               alreadyUsed.add(it)
               rightCache[it] = Pair(y, x)
            }
         }
      }
   }

   fun encode(leftLetter: Char, rightLetter: Char): Pair<Char, Char> {
      return mapCoords(leftLetter, rightLetter) { oldX -> (oldX + 1) % width }
   }

   fun decode(leftLetter: Char, rightLetter: Char): Pair<Char, Char> {
      return mapCoords(leftLetter, rightLetter) { oldX -> (oldX - 1 + width) % width }
   }

   private fun mapCoords(leftLetter: Char, rightLetter: Char, newXifEqual: (Int) -> (Int)): Pair<Char, Char> {
      val leftCoords = leftCache[leftLetter]!!
      val rightCoords = rightCache[rightLetter]!!
      val newLeftLetter: Char
      val newRightLetter: Char
      if (leftCoords.first == rightCoords.first && leftCoords.second == rightCoords.second) {
         val newX = newXifEqual(leftCoords.second)
         newLeftLetter = leftMatrix[rightCoords.first][newX]
         newRightLetter = rightMatrix[rightCoords.first][newX]
      } else if (leftCoords.first == rightCoords.first) {
         newLeftLetter = leftMatrix[leftCoords.first][rightCoords.second]
         newRightLetter = rightMatrix[rightCoords.first][leftCoords.second]
      } else {
         newLeftLetter = leftMatrix[rightCoords.first][leftCoords.second]
         newRightLetter = rightMatrix[leftCoords.first][rightCoords.second]
      }
      return Pair(newLeftLetter, newRightLetter)
   }

   override fun toString(): String {
      val result = StringBuilder()
      for (i in leftMatrix.indices) {
         val left = leftMatrix[i].joinToString(separator = " ")
         val right = rightMatrix[i].joinToString(separator = " ")
         result.append("$left\t\t$right\n")
      }
      return result.toString()
   }

   fun containsLetter(letter: Char) = letterSet.contains(letter)

   fun getAdditionalLetter() = letterString.last()
}

class RussianSquarePair : RectanglePair(
   width = 6,
   height = 6,
   letterString = "абвгдеёжзийклмнопрстуфхчцшщъыьэюя., "
)
