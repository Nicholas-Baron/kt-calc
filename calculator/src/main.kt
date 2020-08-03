import kotlin.system.exitProcess

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
        if (shunted.isEmpty()) {
            println("Shunting failed")
            continue
        }

        val results = eval(shunted)

        for (result in results) {

            when (result) {
                is Integer -> println(result.value)
                is Floating -> println(result.value)
                is Quit -> exitProcess(0)
                else -> println("Unusual result returned")
            }

        }

    }
}
