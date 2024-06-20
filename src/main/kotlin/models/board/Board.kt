package models.board

import java.lang.Error
import java.util.SortedMap

enum class Direction {
    LEFT_RIGHT, UP_DOWN
}

object Board {
    private var board: MutableMap<Int, MutableMap<Int, Char>>

    init {
        board = mutableMapOf()
    }

    fun add(
        word: String,
        direction: Direction = Direction.LEFT_RIGHT,
        position: Pair<Int, Int> = Pair(0, 0)
    ): MutableList<Char> {
        if (this.board.isEmpty()) {
            this.board[0] = mutableMapOf()
            var col = 0
            for (char in word) {
                this.board = this.insertCharAt(this.board,0, col, char)
                col++;
            }
            return mutableListOf()
        }

        var boardCopy = this.createCopyOfBoard()
        val unusedTiles: MutableList<Char> = mutableListOf()
        try {
            var col = position.second
            var row = position.first
            for (char in word) {
                if (charExistsAt(boardCopy, row, col)) {
                    if (getCharAt(boardCopy, row, col) == char) {
                        unusedTiles.add(char)
                    } else {
                        throw Exception(
                            "Cannot add '${char}' to [[${row}, ${col}]] as it is occupied by tile '${
                                getCharAt(
                                    boardCopy,
                                    row,
                                    col
                                )
                            }'."
                        )
                    }
                } else {
                    boardCopy = insertCharAt(boardCopy, row, col, char)
                }
                if (direction == Direction.LEFT_RIGHT) {
                    col++;
                } else {
                    row++;
                }
            }
            return unusedTiles
        } catch (e: Exception) {
            println("Error: Failed to add word - ${e.message}")
        }
        return mutableListOf()
    }

    fun print() {
        val sortedBoard = this.sort()
        if (sortedBoard.isEmpty()) {
            println("The board is currently empty.")
            return
        }

        // start by getting the leftmost and rightmost columns so that we know how big of a board to print
        var leftMostCol = sortedBoard[sortedBoard.firstKey()]!!.firstKey()
        var rightMostCol = sortedBoard[sortedBoard.firstKey()]!!.lastKey()
        sortedBoard.forEach { (_, value) ->
            if (value.firstKey() < leftMostCol)
                leftMostCol = value.firstKey()
            if (value.lastKey() > rightMostCol)
                rightMostCol = value.lastKey()
        }
        val topMostRow = sortedBoard.firstKey()
        val bottomMostRow = sortedBoard.lastKey()

        // start printing
        for (i in 0..rightMostCol - leftMostCol + 2) {
            print('_')
        }
        println()
        for (row in topMostRow..bottomMostRow) {
            print('|')
            for (col in leftMostCol..rightMostCol) {
                if (sortedBoard[row]!!.containsKey(col)) {
                    print(sortedBoard[row]!![col])
                } else {
                    print(' ')
                }
            }
            println('|')
        }
        for (i in 0..rightMostCol - leftMostCol + 2) {
            print('-')
        }

    }

    fun reset() {
        board = mutableMapOf()
    }

    fun validate(): Boolean {
        return false
    }

    private fun insertCharAt(board: MutableMap<Int, MutableMap<Int, Char>>, row: Int, col: Int, char: Char): MutableMap<Int, MutableMap<Int, Char>> {
        if (board.containsKey(row) && board[row]!!.containsKey(col)) {
            throw Error("The tile ${board[row]?.get(col)} already exists at [${row}, ${col}].")
        }
        board[row]?.set(col, char)
        return board
    }

    private fun charExistsAt(board: MutableMap<Int, MutableMap<Int, Char>>, row: Int, col: Int): Boolean {
        return board.containsKey(row) && board[row]!!.containsKey(col)
    }

    private fun getCharAt(board: MutableMap<Int, MutableMap<Int, Char>>, row: Int, col: Int): Char {
        if (board.containsKey(row) && board[row]!!.containsKey(col)) {
            return board[row]!![col]!!
        }
        throw Error("No tile exists at [${row}, ${col}].")
    }

    private fun sort(): SortedMap<Int, SortedMap<Int, Char>> {
        val sortedBoard: MutableMap<Int, SortedMap<Int, Char>> = mutableMapOf()
        for ((key, value) in board.entries) sortedBoard[key] = value.toSortedMap()
        return sortedBoard.toSortedMap()
    }

    private fun createCopyOfBoard(): MutableMap<Int, MutableMap<Int, Char>> {
        return board.mapValues { (_, innerMap) ->
            innerMap.toMutableMap()
        }.toMutableMap()
    }
}