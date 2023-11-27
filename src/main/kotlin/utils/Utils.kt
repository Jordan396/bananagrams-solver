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