import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import model.Board
import model.GameMode
import model.Pile
import utils.readUserInputInteger
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.Scanner

private const val INPUT_WORDS_DICTIONARY_FILE_PATH = "resources/words_dictionary.json"
private const val OUTPUT_WORDS_DICTIONARY_FILE_PATH = "resources/processed_words_dictionary.json"
private const val DEFAULT_NUMBER_OF_TILES_FIRST_DRAW = 21

// Global variables
var processedWordMap: MutableMap<String, MutableList<String>> = mutableMapOf()

fun main() {
    val scanner = Scanner(System.`in`)

    // Display instructions to the user
    println("Welcome to the bananagrams solver! 🍌")
    println("Please select an option: ")
    println("1. Prepare data")
    println("2. Play against computer! 💻")
    println("3. Play against player! 🙍‍")
    println("4. Exit")

    print("Enter your choice (1-4): ")

    when (scanner.nextInt()) {
        1 -> prepare()
        2 -> start(GameMode.COMPUTER)
        3 -> start(GameMode.PLAYER)
        4 -> println("Exiting...")
        else -> println("Invalid option selected.")
    }
    return
}

private fun prepare() {
    println("Preparing data...")
    var stringMap: Map<String, String> = mutableMapOf()
    try {
        stringMap = loadRawWords()
    } catch (e: FileNotFoundException) {
        println("Error: File not found - ${e.message}")
    } catch (e: JsonSyntaxException) {
        println("Error: Invalid JSON format - ${e.message}")
    }
    if (stringMap.isEmpty()){
        throw java.lang.Exception("stringMap is empty!")
    }

    // Sort each word by characters in ascending order
    val resultMap: MutableMap<String, MutableList<String>> = mutableMapOf()
    for ((key, _) in stringMap) {
        val sortedWord = String(key.toCharArray().sortedArray())
        resultMap.getOrPut(sortedWord) { mutableListOf() }.add(key)
    }

    // Save to file
    val jsonString = GsonBuilder().setPrettyPrinting().create().toJson(resultMap)
    try {
        File(OUTPUT_WORDS_DICTIONARY_FILE_PATH).writeText(jsonString)
        println("Processed words saved to $OUTPUT_WORDS_DICTIONARY_FILE_PATH")
    } catch (e: IOException) {
        println("Error writing file: ${e.message}")
    } catch (e: Exception) {
        println("An unexpected error occurred: ${e.message}")
    }
}

private fun start(gameMode: GameMode) {
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

    println("======================================================")
    println("================== GAME IS STARTING ==================")
    println("======================================================")

    // Initialise game state
    val board: Board = Board()
    val commonPile: Pile = Pile(gameMode)
    val playerPile: Pile = Pile(gameMode, mapOf())

    println("How many tiles to draw at the start of the game?")
    val tilesDrawnOnFirstDraw = commonPile.draw(readUserInputInteger() ?: DEFAULT_NUMBER_OF_TILES_FIRST_DRAW)
    playerPile.add(tilesDrawnOnFirstDraw)
}

private fun loadRawWords(): Map<String, String> {
    val gson = Gson()
    val json = File(INPUT_WORDS_DICTIONARY_FILE_PATH).readText(Charsets.UTF_8)

    // Use TypeToken to get the correct type for Gson
    val type = object : TypeToken<Map<String, String>>() {}.type
    val stringMap: Map<String, String> = gson.fromJson(json, type)

    return stringMap
}
private fun loadProcessedWords(): MutableMap<String, MutableList<String>> {
    // Read the JSON string from the file
    val jsonString = File(OUTPUT_WORDS_DICTIONARY_FILE_PATH).readText(Charsets.UTF_8)

    // Gson needs to know the specific generic type of your map
    val type = object : TypeToken<MutableMap<String, MutableList<String>>>() {}.type

    // Deserialize the JSON string back into a Map
    val stringMap: MutableMap<String, MutableList<String>> = Gson().fromJson(jsonString, type)

    return stringMap
}