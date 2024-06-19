package words

interface ProcessedWordLoader {
    fun loadProcessedWords(): MutableMap<String, MutableList<String>>
}