package models.pile

import constants.DEFAULT_TILE_PILE_CONFIG
import models.GameMode
import kotlin.Exception
import kotlin.random.Random

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

    fun get(): MutableList<Char>{
        return this.tilePile
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

    fun remove(tiles: MutableList<Char>){
       val tilePileCopy = tilePile.toMutableList()
        for (char in tiles){
            if (!tilePileCopy.remove(char)){
                throw Exception("Cannot remove tile '${char}' from tile pile as tile does not exist.")
            }
        }
        this.tilePile = tilePileCopy
    }


}