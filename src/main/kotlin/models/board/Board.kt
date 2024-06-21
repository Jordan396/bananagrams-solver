package models.board

import java.lang.Error
import java.util.SortedMap

enum class Direction {
    LEFT_RIGHT, UP_DOWN
}

class Board {
    private var board: MutableMap<Int, MutableMap<Int, Char>>

    init {
        board = mutableMapOf()
    }

    fun add(
        word: String,
        tiles: MutableList<Char>,
        direction: Direction = Direction.LEFT_RIGHT,
        position: Pair<Int, Int> = Pair(0, 0)
    ) {
        var boardCopy = this.createCopyOfBoard()

        try {
            if (boardCopy.isEmpty()) {
                boardCopy[0] = mutableMapOf()
                var col = 0
                for (char in word) {
                    if (tiles.remove(char)) {
                        boardCopy = this.insertCharAt(this.board, 0, col, char)
                        col++;
                    } else {
                        throw Exception("Cannot place tile '${char}' at [${0}, ${col}] as the tile is not available.")
                    }
                }
                return
            } else {
                var col = position.second
                var row = position.first
                for (char in word) {
                    if (charExistsAt(boardCopy, row, col)) {
                        if (getCharAt(boardCopy, row, col) != char) {
                            throw Exception(
                                "Expected tile '${char}' to be at [[${row}, ${col}]] but is instead occupied by tile '${
                                    getCharAt(
                                        boardCopy,
                                        row,
                                        col
                                    )
                                }'."
                            )
                        }
                    } else {
                        if (tiles.remove(char)) {
                            boardCopy = insertCharAt(boardCopy, row, col, char)
                        } else {
                            throw Exception("Cannot place tile '${char}' at [${0}, ${col}] as the tile is not available.")
                        }
                    }
                    if (direction == Direction.LEFT_RIGHT) {
                        col++;
                    } else {
                        row++;
                    }
                }
            }

            // after adding, check if there are unused tiles
            if (tiles.isEmpty()){
                this.board = boardCopy
                return
            } else {
                throw Exception("After forming the word ${word}, there are some unused tiles remaining.")
            }
        } catch (e: Exception) {
            println("Error: Failed to add word - ${e.message}")
        }
        return
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

    private fun insertCharAt(
        board: MutableMap<Int, MutableMap<Int, Char>>,
        row: Int,
        col: Int,
        char: Char
    ): MutableMap<Int, MutableMap<Int, Char>> {
        if (board.containsKey(row) && board[row]!!.containsKey(col)) {
            throw Error("The tile ${board[row]?.get(col)} already exists at [${row}, ${col}].")
        }

        if (!board.containsKey(row)) board[row] = mutableMapOf()
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