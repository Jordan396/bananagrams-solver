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

    fun add(word: String, direction: Direction = Direction.LEFT_RIGHT, position: Pair<Int, Int> = Pair(0,0)) {
        if (this.board.isEmpty()){
            this.board[0] = mutableMapOf()
            var col: Int = 0
            for (char in word){
                this.board[0]?.set(col, char)
                col++;
            }
            return
        }
    }

    fun print() {
        val sortedBoard = this.sort()
        if (sortedBoard.isEmpty()){
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
        for (i in 0..rightMostCol - leftMostCol + 2){
            print('_')
        }
        println()
        for (row in topMostRow..bottomMostRow){
            print('|')
            for (col in leftMostCol..rightMostCol){
                if (sortedBoard[row]!!.containsKey(col)){
                    print(sortedBoard[row]!![col])
                } else {
                    print(' ')
                }
            }
            println('|')
        }
        for (i in 0..rightMostCol - leftMostCol + 2){
            print('-')
        }

    }

    fun reset() {
        board = mutableMapOf()
    }

    fun validate(): Boolean{
        return false
    }

    private fun insertCharAt(row: Int, col: Int, char: Char){
        if (this.board.containsKey(row) && this.board[row]!!.containsKey(col)){
            throw Error("The tile ${this.board[row]?.get(col)} already exists at [${row}, ${col}].")
        }
        this.board[row]?.set(col, char)
    }

    private fun sort(): SortedMap<Int, SortedMap<Int, Char>>{
        val sortedBoard: MutableMap<Int, SortedMap<Int, Char>> = mutableMapOf()
        for ((key, value) in board.entries) sortedBoard[key] = value.toSortedMap()
        return sortedBoard.toSortedMap()
    }
}