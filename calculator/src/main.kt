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
        println(tokens.joinToString(separator = " "))
        val shunted = shunt(tokens)
        while(shunted.isNotEmpty()) {
            println(myList.dequeueOrNull())
        }
    }
}
