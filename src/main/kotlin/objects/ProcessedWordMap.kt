package objects

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileNotFoundException

object ProcessedWordMap {
    var processedWordMap: MutableMap<String, MutableList<String>> = mutableMapOf()

    init {
        println("Loading processed words...")
        try {
            processedWordMap = loadProcessedWords()
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

    private fun loadProcessedWords(): MutableMap<String, MutableList<String>> {
        // Read the JSON string from the file
        val jsonString = File(constants.OUTPUT_WORDS_DICTIONARY_FILE_PATH).readText(Charsets.UTF_8)

        // Gson needs to know the specific generic type of your map
        val type = object : TypeToken<MutableMap<String, MutableList<String>>>() {}.type

        // Deserialize the JSON string back into a Map
        return Gson().fromJson(jsonString, type)
    }

    fun processedWordMap() : MutableMap<String, MutableList<String>> {
        return this.processedWordMap
    }
}