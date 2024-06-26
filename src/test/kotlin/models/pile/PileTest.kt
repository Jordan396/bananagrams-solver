package models.pile

import models.GameMode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

class PileTest {

    @Test
    fun `draw valid number of tiles`() {
        val tilePileConfig = mapOf('a' to 3, 'b' to 2)
        val pile = Pile(GameMode.COMPUTER, tilePileConfig)
        val drawnTiles = pile.draw(3)
        assertEquals(3, drawnTiles.size)
        assertEquals(2, pile.getPileSize())
    }

    @Test
    fun `attempt to draw more tiles than are in the pile`() {
        val tilePileConfig = mapOf('a' to 3, 'b' to 2)
        val pile = Pile(GameMode.COMPUTER, tilePileConfig)
        val drawnTiles = pile.draw(6)

        assertEquals(5, drawnTiles.size)
        assertEquals(0, pile.getPileSize())
    }

    @Test
    fun `attempt to draw a non-positive number of tiles`() {
        val tilePileConfig = mapOf('a' to 3, 'b' to 2)
        val pile = Pile(GameMode.COMPUTER, tilePileConfig)
        val exception = assertThrows<Exception> {
            pile.draw(-1)
        }
        assertEquals("Player must draw 1 or more tiles!", exception.message)
    }

    @Test
    fun `add tiles to existing pile`() {
        val tilePileConfig = mapOf('a' to 3, 'b' to 2)
        val pile = Pile(GameMode.COMPUTER, tilePileConfig)
        val tilesToAdd : MutableList<Char> = mutableListOf()
        tilesToAdd.add('c')
        tilesToAdd.add('d')
        pile.add(tilesToAdd)
        assertEquals(7, pile.getPileSize())
        assertEquals(pile.get(), mutableListOf('a','a','a','b','b','c','d'))
    }

    @Test
    fun `remove tiles from existing pile`() {
        val tilePileConfig = mapOf('a' to 3, 'b' to 2)
        val pile = Pile(GameMode.COMPUTER, tilePileConfig)
        val tilesToRemove : MutableList<Char> = mutableListOf()
        tilesToRemove.add('a')
        tilesToRemove.add('b')
        pile.remove(tilesToRemove)
        assertEquals(3, pile.getPileSize())
        assertEquals(pile.get(), mutableListOf('a','a','b'))
    }
}
