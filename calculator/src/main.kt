fun main() {
    println("Kotlin Calculator")

    while (true) {
        val input = readLine()
        if (input == null) {
            println("Error reading input. Try again")
            continue
        }

        val tokens = parse(input)

        if (tokens.isEmpty()) {
            println("Parsing failed")
            continue
        }
        val shunted = shunt(tokens)
        println("Debug shunted: ${shunted.joinToString()}")
        val result = eval(shunted)

        if (result is Integer) println(result.value)
        else if (result is Floating) println(result.value)
        else println("Unusual result returned")
    }
}
