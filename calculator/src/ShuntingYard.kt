fun shunt(inputList: List<Token>): List<Token> {
    val outputQueue = mutableListOf<Token>()
    val operatorStack = mutableListOf<Token>()

    var expectedOperands = 0

    for (x in inputList) {
        when (x) {
            is Floating, is Integer -> {
                outputQueue.enqueue(x)
                if (expectedOperands > 0) expectedOperands--
            }
            is Function, is LeftParenthesis -> {
                operatorStack.push(x)
                if (expectedOperands < 1) expectedOperands++
            }
            is BinaryOp -> {
                while (operatorStack.isNotEmpty() &&
                        (operatorStack.peek().precedence() > x.precedence() || operatorStack.peek().precedence() == x.precedence())
                        && operatorStack.peek() !is LeftParenthesis) {
                    outputQueue.enqueue(operatorStack.pop())
                }
                operatorStack.push(x)
                expectedOperands++
            }
            is RightParenthesis -> {
                while (operatorStack.peekOrNull() !is LeftParenthesis && operatorStack.peekOrNull() != null) {
                    outputQueue.enqueue(operatorStack.pop())
                    if (operatorStack.peekOrNull() is LeftParenthesis)
                        operatorStack.popOrNull()
                }
            }
        }
    }

    while (operatorStack.isNotEmpty()) {
        outputQueue.enqueue(operatorStack.pop())
    }

    if (expectedOperands != 0) println("Expression may be unbalanced. Expected $expectedOperands more operands")

    return outputQueue
}


//private precedence functions
private fun Token.precedence() = when (this) {
    is LeftParenthesis, is RightParenthesis -> 25
    is Exponentiation -> 20
    is Multiply, is Division, is Modulo, is Remainder,
    is Sine, is Cosine, is Tangent, is SquareRoot, is AbsVal -> 15
    is Minus, is Plus -> 10
    is LeftShift, is RightShift -> 5
    is BitAnd -> 4
    is BitOr -> 3
    else -> TODO("Asked for the precedence of $this")
}