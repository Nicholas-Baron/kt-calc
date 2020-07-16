// Returns the list of tokens
fun parse(input: CharSequence): List<Token> {
    var currentIndex = 0
    val resultList = mutableListOf<Token>()

    fun invalidParse(message: String): List<Token> {
        println("Around character $currentIndex: $message")
        return emptyList()
    }

    nextChar@ while (currentIndex < input.length) {
        val currentChar = input[currentIndex]
        when {
            currentChar.isWhitespace() -> {
                currentIndex++
                continue@nextChar
            }
            currentChar.isLetter() -> {
                val (symbol, index) = input.readWhile(start = currentIndex) { it.isLetter() }

                currentIndex = index

                val token = functionMap[symbol]
                resultList.add(token ?: return invalidParse("Unrecognized token: $symbol"))
            }
            currentChar.isDigit() || currentChar == '.' -> {
                val (symbol, index) = input.readWhile(start = currentIndex) { it.isDigit() || it == '.' }

                currentIndex = index

                if (symbol.endsWith('.'))
                    return invalidParse("Please use ${symbol}0")

                if (symbol.startsWith('.'))
                    return invalidParse("Please use 0${symbol}")

                val token = tokenFromDigits(symbol)
                resultList.add(token ?: return invalidParse("Unrecognized number $symbol"))
            }
            else -> {
                when (currentChar) {
                    in operatorMap.keys -> {
                        val token = operatorMap[currentChar]
                        resultList.add(token
                                ?: return invalidParse("$currentChar in operator map, but has no associated token"))
                    }
                    '<', '>' -> {
                        currentIndex++
                        val nextChar = input[currentIndex]
                        val token = if (nextChar != currentChar) {
                            return invalidParse("'<' and '>' are not valid single operators. Expected either '<<' or '>>'")
                        } else when (nextChar) {
                            '<' -> LeftShift
                            '>' -> RightShift
                            else -> return invalidParse("$nextChar is not either '<' or '>'")
                        }
                        resultList.add(token)
                    }
                    '-' -> {
                        // TODO: Simplify this logic when the token system improves
                        // If the list is empty, this must be a negative
                        if (resultList.isEmpty() || (resultList.last() !is Floating && resultList.last() !is Integer)) {
                            var (symbol, index) = input.readWhile(start = currentIndex + 1) { it.isDigit() || it == '.' }

                            if (symbol.startsWith('.')) return invalidParse("Please use -0$symbol")
                            if (symbol.endsWith('.')) return invalidParse("Please use -${symbol}0")

                            symbol = "-$symbol"
                            currentIndex = index

                            val token = tokenFromDigits(symbol)
                            resultList.add(token ?: return invalidParse("Unrecognized number: $symbol"))
                        } else if (resultList.last() is Integer || resultList.last() is Floating) {
                            // If the last token is a value, this must be a subtraction
                            resultList.add(Minus)
                        } else TODO("Unimplemented case for '-'")
                    }
                    else -> return invalidParse("Unrecognized character: $currentChar")
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