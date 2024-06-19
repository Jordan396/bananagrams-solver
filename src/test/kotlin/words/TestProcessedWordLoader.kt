package words;

class TestProcessedWordLoader(private val words:MutableMap<String, MutableList<String>>) : ProcessedWordLoader {
    override fun loadProcessedWords(): MutableMap<String, MutableList<String>> {
        return words;
    }
}
