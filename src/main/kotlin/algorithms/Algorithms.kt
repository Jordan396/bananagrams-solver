package algorithms

import objects.ProcessedWordMap

fun findLongestWord(input: String, unusedTiles: MutableList<Char>): Pair<String?, MutableList<Char>> {
    if (input.isEmpty())
        return Pair(null, unusedTiles)

    if (ProcessedWordMap.processedWordMap().containsKey(input)){
        return Pair(ProcessedWordMap.processedWordMap[input]?.get(0), unusedTiles)
    } else {
        for (i in 0 until input.length){
            val newString = utils.removeCharAtIndex(input, i)
            unusedTiles.add(input[i])
            findLongestWord(newString, unusedTiles)
        }
    }

    return Pair(null, unusedTiles)
}