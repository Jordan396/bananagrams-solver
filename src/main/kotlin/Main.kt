import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.Scanner

private const val WORDS_DICTIONARY_FILE_PATH = "resources/words_dictionary.json"
fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    // Display instructions to the user
    println("Welcome to the Bananagrams solver! 🍌")
    println("Please select an option: ")
    println("1. Prepare data")
    println("2. Start the game!")
    println("3. Exit")

    print("Enter your choice (1-3): ")
    val option = scanner.nextInt()

    when (option) {
        1 -> prepareData()
        2 -> start()
        3 -> println("Exiting...")
        else -> println("Invalid option selected.")
    }
    return
}

fun prepareData(){
    println("Preparing data...")
    val gson = Gson()
    val json = File(WORDS_DICTIONARY_FILE_PATH).readText(Charsets.UTF_8)

    // Use TypeToken to get the correct type for Gson
    val type = object : TypeToken<Map<String, String>>() {}.type
    val stringMap: Map<String, String> = gson.fromJson(json, type)

    // Now you can use the map
    var count = 0
    for ((key, value) in stringMap) {
        if (count < 3) {
            println("$key: $value")
            count++
        } else {
            break
        }
    }
}

fun start(){
    println("Starting the game...")
}