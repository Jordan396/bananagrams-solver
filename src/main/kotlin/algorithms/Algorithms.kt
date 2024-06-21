package algorithms

import words.ProcessedWordMap
import java.util.*

fun findLongestWord(wordMap: ProcessedWordMap, input: String): String {
    if (input.isEmpty())
        return ""

    // initialise with base word
    val queue: Queue<String> = LinkedList()
    queue.add(input)

    // optimisation, track all processed strings
    val set: MutableSet<String> = mutableSetOf(input)

    // attempt to form the longest word
    while (!queue.isEmpty()){
        val current = queue.peek()
        queue.remove()

        if (current.isEmpty()){
            return ""
        }

        if (wordMap.processedWordMap().containsKey(current)){
            return wordMap.processedWordMap()[current]?.get(0)!!
        } else {
            for (i in current.indices){
                val newString = utils.removeCharAtIndex(current, i)
                if (!set.contains(newString)){
                    queue.add(newString)
                    set.add(newString)
                }
            }
        }
    }


    return ""
}