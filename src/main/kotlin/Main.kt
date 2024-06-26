import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import constants.OUTPUT_WORDS_DICTIONARY_FILE_PATH
import models.board.Board
import models.GameMode
import models.board.Direction
import models.pile.Pile
import utils.readUserInputInteger
import utils.sortThenCombine
import words.FileProcessedWordLoader
import words.ProcessedWordMap
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.Scanner

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
    if (stringMap.isEmpty()) {
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
        File(constants.OUTPUT_WORDS_DICTIONARY_FILE_PATH).writeText(jsonString)
        println("Processed words saved!")
    } catch (e: IOException) {
        println("Error writing file: ${e.message}")
    } catch (e: Exception) {
        println("An unexpected error occurred: ${e.message}")
    }
}

private fun start(gameMode: GameMode) {
    println("======================================================")
    println("================== GAME IS STARTING ==================")
    println("======================================================")

    // early return for gameMode == PLAYER
    if (gameMode == GameMode.PLAYER){
        println("Player vs Player mode is still in development. Check in again later!")
        return
    }

    // Initialise game state
    val board = Board()
    val commonPile = Pile(gameMode)
    val playerPile = Pile(gameMode, mapOf())
    val wordMap = ProcessedWordMap(FileProcessedWordLoader(OUTPUT_WORDS_DICTIONARY_FILE_PATH));

    println("How many tiles to draw at the start of the game?")
    val tilesDrawnOnFirstDraw = commonPile.draw(readUserInputInteger() ?: constants.DEFAULT_NUMBER_OF_TILES_FIRST_DRAW)
    playerPile.add(tilesDrawnOnFirstDraw)
    playerPile.print()

    // play...
    while (commonPile.getPileSize() != 0) {
        println("Player's current configuration: ")
        board.print()
        playerPile.print()

        if (playerPile.getPileSize() == 0) {
            println("Peel!")
            val newTiles = commonPile.draw(3)
            playerPile.add(newTiles)
            playerPile.print()
        }

        var added = false
        var longestWord: Pair<String, Int>

        // initial move, just find the longest word and put it on the board
        if (board.isEmpty()) {
            longestWord = algorithms.findLongestWord(wordMap, sortThenCombine(playerPile.get()))
            board.add(longestWord.first, longestWord.first.toMutableList())
            playerPile.remove(longestWord.first.toMutableList())
            continue
        }

        // attempt to add to cols first
        val cols = board.getCols()
        for (col in cols) {
            val tilesInCol = board.getTilesInCol(col)
            val tilesInColStart = tilesInCol.first().first
            val tilesInColOffset: MutableList<Pair<Int, Char>> = mutableListOf()
            for (tile in tilesInCol) {
                tilesInColOffset.add(Pair(tile.first - tilesInColStart, tile.second))
            }
            longestWord = algorithms.findLongestWord(wordMap, sortThenCombine(playerPile.get()), tilesInColOffset)
            if (longestWord.first != "") {
                println("Adding ${longestWord.first} to column $col...")
                added = true
                val remainingTiles = board.add(
                    longestWord.first,
                    playerPile.get(),
                    Direction.UP_DOWN,
                    Pair(longestWord.second + tilesInColStart, col)
                )
                val usedTiles = utils.removeElements(playerPile.get(), remainingTiles)
                playerPile.remove(usedTiles)
                break
            }
        }

        if (added)
            continue

        // attempt to add to rows
        val rows = board.getRows()
        for (row in rows) {
            val tilesInRow = board.getTilesInRow(row)
            val tilesInRowStart = tilesInRow.first().first
            val tilesInRowOffset: MutableList<Pair<Int, Char>> = mutableListOf()
            for (tile in tilesInRow) {
                tilesInRowOffset.add(Pair(tile.first - tilesInRowStart, tile.second))
            }
            longestWord = algorithms.findLongestWord(wordMap, sortThenCombine(playerPile.get()), tilesInRowOffset)
            if (longestWord.first != "") {
                println("Adding ${longestWord.first} to row $row...")
                val remainingTiles = board.add(
                    longestWord.first,
                    playerPile.get(),
                    Direction.LEFT_RIGHT,
                    Pair(row, longestWord.second + tilesInRowStart)
                )
                val usedTiles = utils.removeElements(playerPile.get(), remainingTiles)
                playerPile.remove(usedTiles)
                break
            }
        }

        // there are no more playable moves
        println("No more playable moves, exiting...")
        break;
    }

    println("Final board configuration: ")
    board.print()

    if (0 == playerPile.getPileSize()) {
        println("You win!")
    } else {
        println("You have some tiles remaining after the game ended. You lose!")
        playerPile.print()
    }
}

private fun loadRawWords(): Map<String, String> {
    val gson = Gson()
    val json = File(constants.INPUT_WORDS_DICTIONARY_FILE_PATH).readText(Charsets.UTF_8)

    // Use TypeToken to get the correct type for Gson
    val type = object : TypeToken<Map<String, String>>() {}.type

    return gson.fromJson(json, type)
}