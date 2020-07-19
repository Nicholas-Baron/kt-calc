fun main()
{
    var one = Integer(1)
    var plus = Plus()
    var two = Integer(2)
    var times = Multiply()
    var three = Integer(3)
    var div = Division()
    var four = Integer(4)
    shunt(mutableListOf(one, plus, two,times,three, div, four))
}
fun shunt (inputList: List<Token>)
{
    var outputQueue= mutableListOf<Token>()
    var operatorStack = mutableListOf<Token>()

    for(x in inputList)
    {
        if(x is Floating || x is Integer)
            outputQueue.enqueue(x)
        else if(x is Function)
            operatorStack.push(x)
        else if(x is BinaryOp) {
            while ((operatorStack.isNotEmpty()) &&
                    ((operatorStack.peek().precedence() > x.precedence() || operatorStack.peek().precedence() == x.precedence()))
                    && (operatorStack.peek() !is LeftParenthesis))
            {
                outputQueue.enqueue(operatorStack.pop())
            }
            operatorStack.push(x)
        }
        else if (x is LeftParenthesis)
            operatorStack.push(x)
        else if (x is RightParenthesis)
            while(operatorStack.peek() !is LeftParenthesis)
            {
                outputQueue.enqueue(operatorStack.pop())
                if(operatorStack.peek() is LeftParenthesis)
                    operatorStack.pop()
            }
    }
    while(operatorStack.isNotEmpty())
    {
        outputQueue.enqueue(operatorStack.pop())
    }
    while (outputQueue.isNotEmpty())
    {
        println(outputQueue.dequeue())
    }
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