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

                currentIndex = index

                val token = functionMap[symbol];
                if (token != null) resultList.add(token)
                else println("Unrecognized token: $symbol")
            }
            currentChar.isDigit() -> {
                val (symbol, index) = input.readWhile(start = currentIndex) { it.isDigit() || it == '.' }

                val token = tokenFromDigits(symbol)
                currentIndex = index

                if (token != null) resultList.add(token)
                else println("Unrecognized number $symbol")
            }
            else -> {
                when (currentChar) {
                    in operatorMap.keys -> resultList.add(operatorMap[currentChar]
                            ?: error("$currentChar in operator map, but has no associated token"))
                    else -> println("Unrecognized character: $currentChar")
                }
                currentIndex++
            }
        }
    }

    return resultList
}

// Non-special single character operators
private val operatorMap = mapOf(
        '+' to Plus, '*' to Multiply, '/' to Division, '%' to Remainder, '^' to Exponentiation,
        '&' to BitAnd, '|' to BitOr, '(' to LeftParenthesis, ')' to RightParenthesis
)

// Functions
private val functionMap = mapOf(
        "sqrt" to SquareRoot, "abs" to AbsVal, "sin" to Sine, "cos" to Cosine, "tan" to Tangent
)

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

private fun tokenFromDigits(symbol: String) =
        if (symbol.contains('.')) {
            symbol.toFloatOrNull()?.let { Floating(it) }
        } else {
            symbol.toIntOrNull()?.let { Integer(it) }
        }