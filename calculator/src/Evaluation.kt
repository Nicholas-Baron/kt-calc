import kotlin.math.*

fun eval(tokens: List<Token>): List<Token> {

    val stack = mutableListOf<Token>()
    val evaluated = mutableListOf<Token>()

    try {
        for (token in tokens) {

            when (token) {

                is Integer, is Floating -> stack.push(token) //push object to stack if its an operand

                is Sequence, is Quit -> {
                    if (stack.isNotEmpty()) {
                        val res = stack.pop()
                        evaluated.add(res)
                    }
                    if (token is Quit) {
                        evaluated.add(token)
                        return evaluated
                    }
                }

                //token is a binary operator
                is BinaryOp -> {

                    val rhs = stack.pop()
                    val lhs = stack.pop()

                    //If statements smart cast for different types of input
                    if (rhs is Integer && lhs is Integer) {
                        when (token) {
                            is Plus -> stack.push(Integer(lhs.value + rhs.value))
                            is Minus -> stack.push(Integer(lhs.value - rhs.value))
                            is Multiply -> stack.push(Integer(lhs.value * rhs.value))
                            is Division -> stack.push(Integer(lhs.value / rhs.value))
                            is Exponentiation -> stack.push(lhs.pow(rhs))
                            is LeftShift -> stack.push(Integer(lhs.value shl rhs.value))
                            is RightShift -> stack.push(Integer(lhs.value shr rhs.value))
                            is BitAnd -> stack.push(Integer(lhs.value and rhs.value))
                            is BitOr -> stack.push(Integer(lhs.value or rhs.value))
                            else -> throw Exception("Unexpected operation $token on $lhs and $rhs")
                        }
                    } else if (lhs is Floating && rhs is Floating) {

                        when (token) {
                            is Plus -> stack.push(Floating(lhs.value + rhs.value))
                            is Minus -> stack.push(Floating(lhs.value - rhs.value))
                            is Multiply -> stack.push(Floating(lhs.value * rhs.value))
                            is Division -> stack.push(Floating(lhs.value / rhs.value))
                            is Exponentiation -> {
                                val res = lhs.value.pow(rhs.value)
                                stack.push(Floating(res))
                            }
                            else -> throw Exception("Unexpected operation $token on $lhs and $rhs")
                        }
                    } else if (lhs is Integer && rhs is Floating) {
                        when (token) {
                            is Plus -> stack.push(Floating(lhs.value + rhs.value))
                            is Minus -> stack.push(Floating(lhs.value - rhs.value))
                            is Multiply -> stack.push(Floating(lhs.value * rhs.value))
                            is Division -> stack.push(Floating(lhs.value / rhs.value))
                            is Exponentiation -> {
                                val res = lhs.value.toFloat().pow(rhs.value)
                                stack.push(Floating(res))
                            }
                            else -> throw Exception("Unexpected operation $token on $lhs and $rhs")
                        }
                    } else if (lhs is Floating && rhs is Integer) {
                        when (token) {
                            is Plus -> stack.push(Floating(lhs.value + rhs.value))
                            is Minus -> stack.push(Floating(lhs.value - rhs.value))
                            is Multiply -> stack.push(Floating(lhs.value * rhs.value))
                            is Division -> stack.push(Floating(lhs.value / rhs.value))
                            is Exponentiation -> {
                                val res = lhs.value.pow(rhs.value)
                                stack.push(Floating(res))
                            }
                            else -> throw Exception("Unexpected operation $token on $lhs and $rhs")
                        }
                    }
                }
                is Function -> {
                    val functionMap = mapOf<Function, (Float) -> Floating>(
                            Sine to { x -> Floating(sin(x)) },
                            Cosine to { x -> Floating(cos(x)) },
                            Tangent to { x -> Floating(tan(x)) },
                            SquareRoot to { x -> Floating(sqrt(x)) }
                    )
                    when (val x = stack.pop()) {
                        is Integer -> {
                            when (token) {
                                in functionMap.keys -> {
                                    val function = functionMap[token]
                                            ?: throw Exception("Could not find $token in function map") // should never happen

                                    stack.push(function(x.value.toFloat()))
                                }
                                is AbsVal -> stack.push(Integer(abs(x.value)))
                                else -> throw Exception("Unexpected $token as function")
                            }
                        }
                        is Floating -> {
                            when (token) {
                                in functionMap.keys -> {
                                    val function = functionMap[token]
                                            ?: throw Exception("Could not find $token in function map") // should never happen

                                    stack.push(function(x.value))
                                }
                                is AbsVal -> stack.push(Floating(abs(x.value)))
                                else -> throw Exception("Unexpected $token as function")
                            }
                        }
                    }
                }
                else -> throw Exception("Unexpected token $token in evaluation")
            }
        }
        if (stack.isNotEmpty())
            evaluated.add(stack.pop())
        return evaluated
    } catch (e: Exception) {
        println("Invalid expression")
        throw e
    }

}

private fun Integer.pow(exp: Integer) = when {
    exp.value < 0 -> Floating(value = this.value.toFloat().pow(exp.value))
    exp.value == 0 -> Integer(1)
    else -> Integer(value = this.value.toFloat().pow(exp.value).toInt())
}

