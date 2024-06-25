package algorithms

import utils.sortThenCombine
import words.ProcessedWordMap
import java.util.*

private fun relax(
    wordMap: ProcessedWordMap,
    input: String,
    conditions: MutableList<Pair<Int, Char>>
): Pair<String?, Int> {
    val queue: Queue<Pair<MutableList<Pair<Int, Char>>, Pair<Int?, Int?>>> = LinkedList()

    // start by having all constraints, no bounds
    queue.add(Pair(conditions, Pair(null, null)))
    while (!queue.isEmpty()) {
        val current = queue.peek()
        queue.remove()

        // process it, add letters from constraints and then check if constraints can be met
        var inputWithConstraints = input
        for (condition in conditions) {
            inputWithConstraints += condition.second
        }
        inputWithConstraints = sortThenCombine(inputWithConstraints.toMutableList())

        // check if a word exists
        if (wordMap.processedWordMap().containsKey(inputWithConstraints)){
            for (word in wordMap.processedWordMap()[inputWithConstraints]!!) {
                val indexWhereWordMeetsCondition = wordMeetsConditionsAt(word, current.first, current.second)
                if (indexWhereWordMeetsCondition != null) {
                    return Pair(word, indexWhereWordMeetsCondition)
                }
            }
        }

        // relax the constraint at either end then try again
        if (current.first.size > 1){
            val removeFirstConstraint = current.first.toMutableList()
            removeFirstConstraint.removeAt(0)
            queue.add(Pair(removeFirstConstraint, Pair(current.first[0].first + 2,current.second.second)))

            val removeLastConstraint = current.first.toMutableList()
            removeLastConstraint.removeAt(current.first.size - 1)
            queue.add(Pair(removeLastConstraint, Pair(current.second.first, current.first.last().first - 2)))
        }
    }

    return Pair(null, 0)
}

private fun wordMeetsConditionsAt(
    word: String,
    conditions: MutableList<Pair<Int, Char>>,
    bounds: Pair<Int?, Int?>
): Int? {
    for (i in word.indices) {
        var condition = 0
        var valid = true

        // check if we can find a word that satisfies all conditions
        if (word[i] == conditions[condition].second) {
            condition++;
            while (condition != conditions.size) {
                val expectedPos = i + conditions[condition].first - conditions[0].first
                if (expectedPos < word.length) {
                    if (word[expectedPos] != conditions[condition].second) {
                        valid = false;
                        break;
                    }
                } else {
                    valid = false;
                    break;
                }
                condition++;
            }

            // check if all conditions were satisfied
            if (condition != conditions.size) {
                valid = false;
            }

            // now check if the boundary conditions are satisfied
            if (bounds.first != null) {
                if (conditions[0].first - i < bounds.first!!) {
                    valid = false
                }
            }
            if (bounds.second != null) {
                if (conditions[0].first - i + word.length > bounds.second!!) {
                    valid = false;
                }
            }

            if (valid) {
                return conditions[0].first - i;
            }
        }
    }
    return null
}

fun findLongestWord(
    wordMap: ProcessedWordMap,
    input: String,
    conditions: MutableList<Pair<Int, Char>> = mutableListOf()
): Pair<String, Int> {


    if (input.isEmpty())
        return Pair("", 0)

    // initialise with base word
    val queue: Queue<String> = LinkedList()
    queue.add(input)

    // optimisation, track all processed strings
    val set: MutableSet<String> = mutableSetOf()

    // attempt to form the longest word
    while (!queue.isEmpty()) {
        val current = queue.peek()
        queue.remove()

        // terminating condition, no word can be formed
        if (current.isEmpty()) {
            return Pair("", 0)
        }

        // if there are no conditions, simply return
        if (wordMap.processedWordMap().containsKey(current)) {
            if (conditions.isEmpty()) {
                return Pair(wordMap.processedWordMap()[current]?.get(0)!!, 0)
            }
        }

        // slowly relax constraints from the ends
        val result = relax(wordMap, current, conditions)
        if (result.first != null) return Pair(result.first!!, result.second)

        // only if no condition can be met, then relax use fewer tiles
        set.add(current)
        for (i in current.indices) {
            val newString = utils.removeCharAtIndex(current, i)
            if (!set.contains(newString)) {
                queue.add(newString)
            }
        }
    }

    return Pair("", 0)
}

