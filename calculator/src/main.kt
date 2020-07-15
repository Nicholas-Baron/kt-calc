fun main() {
    println("Kotlin Calculator")

    while (true) {
        val input = readLine()
        if (input == null) {
            println("Error reading input. Try again")
            continue
        }

        val tokens = parse(input)

        if (tokens.isEmpty())
            println("Parsing failed")
        else
            println(tokens.joinToString(separator = " "))
    }
}