package words

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class FileProcessedWordLoader(private val filePath: String) : ProcessedWordLoader {
    override fun loadProcessedWords(): MutableMap<String, MutableList<String>> {
        // Read the JSON string from the file
        val jsonString = File(filePath).readText(Charsets.UTF_8)

        // Gson needs to know the specific generic type of your map
        val type = object : TypeToken<MutableMap<String, MutableList<String>>>() {}.type

        // Deserialize the JSON string back into a Map
        return Gson().fromJson(jsonString, type)
    }
}