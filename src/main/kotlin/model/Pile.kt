package model

import java.lang.Exception
import kotlin.random.Random

private val DEFAULT_TILE_PILE_CONFIG = mapOf(
    'a' to 13,
    'b' to 3,
    'c' to 3,
    'd' to 6,
    'e' to 18,
    'f' to 3,
    'g' to 4,
    'h' to 3,
    'i' to 12,
    'j' to 2,
    'k' to 2,
    'l' to 5,
    'm' to 3,
    'n' to 8,
    'o' to 11,
    'p' to 3,
    'q' to 2,
    'r' to 9,
    's' to 6,
    't' to 9,
    'u' to 6,
    'v' to 3,
    'w' to 3,
    'x' to 2,
    'y' to 3,
    'z' to 2
    )

class Pile(gameMode: GameMode, tilePileConfig: Map<Char, Int> = DEFAULT_TILE_PILE_CONFIG) {
    private var tilePile: MutableList<Char> = mutableListOf()

    init {
        if (gameMode.equals(GameMode.COMPUTER)) {
            tilePile = tilePileConfig.flatMap { (char, count) -> List(count) { char } }.toMutableList()
        }
    }

    fun getPileSize(): Int {
        return this.tilePile.size
    }

    fun draw(numberOfTilesToDraw: Int): MutableList<Char> {
        val drawnTiles: MutableList<Char> = mutableListOf()

        if (numberOfTilesToDraw <= 0){
            throw Exception("Player must draw 1 or more tiles!")
        }
        if (tilePile.size < numberOfTilesToDraw){
            throw Exception("Size of tile pile is smaller than the number of tiles player wishes to draw!")
        }

        for(i in 0 until numberOfTilesToDraw){
            val randomIndex = Random.nextInt(tilePile.size)
            val randomElement = tilePile[randomIndex]

            // Remove the randomly selected element
            tilePile.removeAt(randomIndex)

            // Add randomly selected element to list
            drawnTiles.add(randomElement)
        }

        return drawnTiles
    }

    fun add(tiles: MutableList<Char>){
        this.tilePile.addAll(tiles)
    }

    fun print(){
        val result = this.tilePile.joinToString(separator = ", ")
        println("Here are your letters: $result")
    }
}