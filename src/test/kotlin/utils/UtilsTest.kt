package utils

import models.GameMode
import models.pile.Pile
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UtilsTest {
    @Test
    fun `removeCharAtIndex should remove characters only at indices between 0 and the size of the given word`() {
        val word = "test"

        val wordWithoutIndexZero = removeCharAtIndex(word, 0)
        assertEquals("est", wordWithoutIndexZero)
        val wordWithoutIndexOne = removeCharAtIndex(word, 1)
        assertEquals("tst", wordWithoutIndexOne)
        val wordWithoutIndexLast = removeCharAtIndex(word, 3)
        assertEquals("tes", wordWithoutIndexLast)

        assertEquals("Index out of bounds", assertThrows<Exception> {
            removeCharAtIndex(word, -1)
        }.message)

        assertEquals("Index out of bounds", assertThrows<Exception> {
            removeCharAtIndex(word, 4)
        }.message)
    }

    @Test
    fun `sortThenCombine sorts the chars in ascending order`() {
        // arrange
        val chars = mutableListOf('z', 'z', 'z', 'a', 'a')

        // act
        val result = sortThenCombine(chars)

        // assert
        assertEquals("aazzz", result)
    }
}