// Returns the list of tokens and whether this sequence is the last sequence
// (ie: the program should close after evaluating the input)
fun parse(input: CharSequence): List<Token> {
    var currentIndex = 0
    val resultList = mutableListOf<Token>()

    nextChar@ while (currentIndex < input.length) {
        val currentChar = input[currentIndex]
        when {
            currentChar.isWhitespace() -> {
                currentIndex++;
                continue@nextChar
            }
            currentChar.isLetter() -> {
                val (symbol, index) = input.readWhile(start = currentIndex) { it.isLetter() }

                val token = tokenFromString(symbol)
                currentIndex = index

                if (token != null) resultList.add(token)
                else println("Unrecognized token: $symbol")
            }
            currentChar.isDigit() -> {
                val (symbol, index) = input.readWhile(start = currentIndex) { it.isDigit() || it == '.' }

                val token = tokenFromDigits(symbol.toString())
                currentIndex = index

                if (token != null) resultList.add(token)
                else println("Unrecognized number $symbol")
            }
            else -> {
                when (currentChar) {
                    '+' -> resultList.add(Plus())
                    else -> println("Unrecognized character: $currentChar")
                }
                currentIndex++
            }
        }
    }

    return resultList
}

// Reads while the condition is true and there are still more characters to read
// Returns the consumed characters and the index which the condition returned false on on
private fun CharSequence.readWhile(start: Int, cond: (Char) -> Boolean): Pair<String, Int> {
    val builder = StringBuilder()
    var index = start
    while (index < length && cond(this[index])) {
        builder.append(this[index])
        index++
    }
    return builder.toString() to index
}

private fun tokenFromString(symbol: String): Token? = when (symbol) {
    "sqrt" -> SquareRoot()
    else -> null
}

private fun tokenFromDigits(symbol: String): Token? =
        if (symbol.contains('.')) {
            symbol.toFloatOrNull().letIfNotNull { Floating(it) }
        } else {
            symbol.toIntOrNull().letIfNotNull { Integer(it) }
        }

private fun <T, R> T?.letIfNotNull(block: (T) -> R): R? = if (this == null) null else block(this)