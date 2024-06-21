package algorithms

import words.ProcessedWordMap
import java.util.*

fun findLongestWord(wordMap: ProcessedWordMap, input: String, conditions: MutableList<Pair<Int, Char>> = mutableListOf()): Pair<String, Int> {
    fun wordMeetsConditionsAt(word: String, conditions: MutableList<Pair<Int, Char>>): Int?{
        for (i in word.indices){
            var condition = 0
            var valid = true
            if (word[i] == conditions[condition].second){
                condition++;
                while (condition != conditions.size){
                    if (i + conditions[condition].first < word.length){
                        if (word[i+conditions[condition].first] != conditions[condition].second){
                            valid = false;
                        }
                    }
                    condition++;
                }

                if (valid){
                    return -i;
                }
            }
        }
        return null
    }

    if (input.isEmpty())
        return Pair("", 0)

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
            return Pair("", 0)
        }

        if (wordMap.processedWordMap().containsKey(current)){
            if (conditions.isEmpty()){
                return Pair(wordMap.processedWordMap()[current]?.get(0)!!, 0)
            } else {
                for (word in wordMap.processedWordMap()[current]!!){
                    val indexWhereWordMeetsCondition = wordMeetsConditionsAt(word, conditions)
                    if (indexWhereWordMeetsCondition != null){
                        return Pair(word, indexWhereWordMeetsCondition)
                    }
                }
            }
        }

        for (i in current.indices){
            val newString = utils.removeCharAtIndex(current, i)
            if (!set.contains(newString)){
                queue.add(newString)
                set.add(newString)
            }
        }
    }

    return Pair("", 0)
}