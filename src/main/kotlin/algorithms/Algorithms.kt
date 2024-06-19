package algorithms

import words.ProcessedWordMap
import java.util.*

fun findLongestWord(wordMap: ProcessedWordMap, input: String): Pair<String?, MutableList<Char>> {
    if (input.isEmpty())
        return Pair(null, mutableListOf())

    val queue: Queue<Pair<String, MutableList<Char>>> = LinkedList()

    // initialise with base word
    queue.add(Pair(input, mutableListOf()))

    // attempt to form the longest word
    while (!queue.isEmpty()){
        val current = queue.peek()
        queue.remove()

        if (current.first.isEmpty()){
            return Pair(null, input.toMutableList())
        }

        if (wordMap.processedWordMap().containsKey(current.first)){
            return Pair(wordMap.processedWordMap()[current.first]?.get(0), current.second)
        } else {
            for (i in current.first.indices){
                val newString = utils.removeCharAtIndex(current.first, i)
                val newUnusedTiles = current.second.toMutableList()
                newUnusedTiles.add(current.first[i])
                queue.add(Pair(newString, newUnusedTiles))
            }
        }
    }


    return Pair(null, input.toMutableList())
}