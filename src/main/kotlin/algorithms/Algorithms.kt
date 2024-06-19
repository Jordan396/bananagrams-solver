package algorithms

import words.ProcessedWordMap
import java.util.*

fun findLongestWord(wordMap: ProcessedWordMap, input: String): Pair<String?, MutableList<Char>> {
    if (input.isEmpty())
        return Pair(null, mutableListOf())

    // initialise with base word
    val queue: Queue<Pair<String, MutableList<Char>>> = LinkedList()
    queue.add(Pair(input, mutableListOf()))

    // optimisation, track all processed strings
    val set: MutableSet<String> = mutableSetOf(input)

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
                if (!set.contains(newString)){
                    val newString = utils.removeCharAtIndex(current.first, i)
                    val newUnusedTiles = current.second.toMutableList()
                    newUnusedTiles.add(current.first[i])
                    queue.add(Pair(newString, newUnusedTiles))
                    set.add(newString)
                }

            }
        }
    }


    return Pair(null, input.toMutableList())
}