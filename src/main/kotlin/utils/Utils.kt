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

fun removeElements(list1: MutableList<Char>, list2: MutableList<Char>): MutableList<Char> {
    val list1Copy = list1.toMutableList()
    val map = list2.groupingBy { it }.eachCount().toMutableMap()

    val iterator = list1Copy.listIterator()
    while (iterator.hasNext()) {
        val element = iterator.next()
        val count = map[element]
        if (count != null && count > 0) {
            iterator.remove()
            map[element] = count - 1
        }
    }
    return list1Copy
}