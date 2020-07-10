// Returns the list of tokens
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
                resultList.add(token ?: error("Unrecognized token: $symbol"))
            }
            currentChar.isDigit() -> {
                val (symbol, index) = input.readWhile(start = currentIndex) { it.isDigit() || it == '.' }

                currentIndex = index

                val token = tokenFromDigits(symbol)
                resultList.add(token ?: error("Unrecognized number $symbol"))
            }
            else -> {
                when (currentChar) {
                    in operatorMap.keys -> {
                        val token = operatorMap[currentChar]
                        resultList.add(token ?: error("$currentChar in operator map, but has no associated token"))
                    }
                    '<', '>' -> {
                        currentIndex++
                        val nextChar = input[currentIndex]
                        val token = if (nextChar != currentChar) {
                            // TODO: Should this return an empty list on error?
                            error("'<' and '>' are not valid single operators. Expected either '<<' or '>>'")
                        } else when (nextChar) {
                            '<' -> LeftShift
                            '>' -> RightShift
                            else -> error("$nextChar is not either '<' or '>'")
                        }
                        resultList.add(token)
                    }
                    else -> error("Unrecognized character: $currentChar")
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
        "sqrt" to SquareRoot, "abs" to AbsVal, "sin" to Sine, "cos" to Cosine, "tan" to Tangent, "mod" to Modulo
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