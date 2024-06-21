package utils

fun readUserInputInteger(): Int? {
    val input = readLine()
    val number = input?.toIntOrNull()

    if (number != null) {
        return number
    } else {
        println("Invalid input.")
    }
    return null
}

fun removeCharAtIndex(str: String, index: Int): String {
    if (index < 0 || index >= str.length) {
        throw IllegalArgumentException("Index out of bounds")
    }
    return str.substring(0, index) + str.substring(index + 1, str.length)
}

fun sortThenCombine(chars: MutableList<Char>): String {
    return chars.toCharArray().sorted().joinToString("")
}