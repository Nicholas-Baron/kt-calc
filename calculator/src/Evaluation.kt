import java.util.*
import kotlin.math.*

fun eval(tokens: List<Token>): List<Token> {

    val stack = ArrayDeque<Token>()
    val evaluated = mutableListOf<Token>()

    try {
        for (token in tokens) {

            when (token) {

                is Integer, is Floating -> stack.push(token) //push object to stack if its an operand

                is Sequence -> {
                    if(stack.isNotEmpty()) {
                        val res = stack.pop()
                        evaluated.add(res)
                    }
                }

                //token is a binary operator
                is BinaryOp -> {

                    val rhs = stack.pop()
                    val lhs = stack.pop()

                    //If statements smart cast for different types of input
                    if ((rhs is Integer) && (lhs is Integer)) {
                        when (token) {
                            is Plus -> stack.push(Integer(lhs.value + rhs.value))
                            is Minus -> stack.push(Integer(lhs.value - rhs.value))
                            is Multiply -> stack.push(Integer(lhs.value * rhs.value))
                            is Division -> stack.push(Integer(lhs.value / rhs.value))
                            is Exponentiation -> {
                                val res = (lhs.value.toFloat()).pow(rhs.value.toFloat())
                                stack.push(Integer(res.toInt()))
                            }
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
                                val res = (lhs.value).pow(rhs.value)
                                stack.push(Integer(res.toInt()))
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
                                val res = (lhs.value.toFloat()).pow(rhs.value)
                                stack.push(Integer(res.toInt()))
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
                                val res = (lhs.value).pow(rhs.value.toFloat())
                                stack.push(Integer(res.toInt()))
                            }
                            else -> throw Exception("Unexpected operation $token on $lhs and $rhs")
                        }
                    }
                }
                is Function -> {
                    val x = stack.pop()

                    when (x) {
                        is Integer -> {
                            when (token) {
                                is Sine -> stack.push(Floating(sin(x.value.toFloat())))
                                is Cosine -> stack.push(Floating(cos(x.value.toFloat())))
                                is Tangent -> stack.push(Floating(tan(x.value.toFloat())))
                                is AbsVal -> stack.push(Integer(abs(x.value)))
                                is SquareRoot -> stack.push(Floating(sqrt(x.value.toFloat())))
                            }
                        }
                        is Floating -> {
                            when (token) {
                                is Sine -> stack.push(Floating(sin(x.value)))
                                is Cosine -> stack.push(Floating(cos(x.value)))
                                is Tangent -> stack.push(Floating(tan(x.value)))
                                is AbsVal -> stack.push(Floating(abs(x.value)))
                                is SquareRoot -> stack.push(Floating(sqrt(x.value)))
                            }
                        }
                    }
                }
            }
        }
        if(stack.isNotEmpty())
            evaluated.add(stack.pop())
        return evaluated
    } catch (e: Exception) {
        println("Invalid expression")
        throw e
    }

}