fun main() {
    println("Kotlin Calculator")

    while (true) {
        val input = readLine()
        if (input == null) {
            println("Error reading input. Try again")
            continue
        }

        val tokens = parse(input)
        println(tokens.joinToString(separator = " "))
    }
}