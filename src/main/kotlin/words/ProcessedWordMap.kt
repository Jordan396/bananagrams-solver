package words

import com.google.gson.JsonSyntaxException
import java.io.FileNotFoundException

class ProcessedWordMap(loader: ProcessedWordLoader) {
    private var processedWordMap: MutableMap<String, MutableList<String>> = mutableMapOf()

    init {
        println("Loading processed words...")
        try {
            processedWordMap = loader.loadProcessedWords()
        } catch (e: FileNotFoundException) {
            println("Error: File not found - ${e.message}")
        } catch (e: JsonSyntaxException) {
            println("Error: Invalid JSON format - ${e.message}")
        }
        if (processedWordMap.isEmpty()){
            throw java.lang.Exception("processedWordMap is empty!")
        }
        println("Processed words loaded!")
    }

    fun processedWordMap() : MutableMap<String, MutableList<String>> {
        return this.processedWordMap
    }
}