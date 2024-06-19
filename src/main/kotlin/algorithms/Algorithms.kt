package algorithms

import words.ProcessedWordMap

fun findLongestWord(wordMap: ProcessedWordMap, input: String, unusedTiles: MutableList<Char>): Pair<String?, MutableList<Char>> {
    if (input.isEmpty())
        return Pair(null, unusedTiles)

    if (wordMap.processedWordMap().containsKey(input)){
        return Pair(wordMap.processedWordMap()[input]?.get(0), unusedTiles)
    } else {
        for (i in input.indices){
            val newString = utils.removeCharAtIndex(input, i)
            unusedTiles.add(input[i])
            findLongestWord(wordMap, newString, unusedTiles)
        }
    }

    return Pair(null, unusedTiles)
}