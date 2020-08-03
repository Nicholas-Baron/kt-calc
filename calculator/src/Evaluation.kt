import java.util.*
import kotlin.math.*

fun eval(tokens: List<Token>): List<Token> {

    val stack = ArrayDeque<Token>()
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

                    val y = stack.pop()
                    val x = stack.pop()

                    //If statements smart cast for different types of input
                    if ((y is Integer) && (x is Integer)) {
                        when (token) {
                            is Plus -> stack.push(Integer(x.value + y.value))
                            is Minus -> stack.push(Integer(x.value - y.value))
                            is Multiply -> stack.push(Integer(x.value * y.value))
                            is Division -> stack.push(Integer(x.value / y.value))
                            is Exponentiation -> {
                                val res = (x.value.toFloat()).pow(y.value.toFloat())
                                stack.push(Integer(res.toInt()))
                            }
                            is LeftShift -> stack.push(Integer(x.value shl y.value))
                            is RightShift -> stack.push(Integer(x.value shr y.value))
                            is BitAnd -> stack.push(Integer(x.value and y.value))
                            is BitOr -> stack.push(Integer(x.value or y.value))
                        }
                    } else if (x is Floating && y is Floating) {

                        when (token) {
                            is Plus -> stack.push(Floating(x.value + y.value))
                            is Minus -> stack.push(Floating(x.value - y.value))
                            is Multiply -> stack.push(Floating(x.value * y.value))
                            is Division -> stack.push(Floating(x.value / y.value))
                            is Exponentiation -> {
                                val res = (x.value).pow(y.value)
                                stack.push(Integer(res.toInt()))
                            }
                            is LeftShift -> throw Exception()
                            is RightShift -> throw Exception()
                            is BitAnd -> throw Exception()
                            is BitOr -> throw Exception()
                        }
                    } else if (x is Integer && y is Floating) {
                        when (token) {
                            is Plus -> stack.push(Floating(x.value + y.value))
                            is Minus -> stack.push(Floating(x.value - y.value))
                            is Multiply -> stack.push(Floating(x.value * y.value))
                            is Division -> stack.push(Floating(x.value / y.value))
                            is Exponentiation -> {
                                val res = (x.value.toFloat()).pow(y.value)
                                stack.push(Integer(res.toInt()))
                            }
                            is LeftShift -> throw Exception()
                            is RightShift -> throw Exception()
                            is BitAnd -> throw Exception()
                            is BitOr -> throw Exception()
                        }
                    } else if (x is Floating && y is Integer) {
                        when (token) {
                            is Plus -> stack.push(Floating(x.value + y.value))
                            is Minus -> stack.push(Floating(x.value - y.value))
                            is Multiply -> stack.push(Floating(x.value * y.value))
                            is Division -> stack.push(Floating(x.value / y.value))
                            is Exponentiation -> {
                                val res = (x.value).pow(y.value.toFloat())
                                stack.push(Integer(res.toInt()))
                            }
                            is LeftShift -> throw Exception()
                            is RightShift -> throw Exception()
                            is BitAnd -> throw Exception()
                            is BitOr -> throw Exception()
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
        if (stack.isNotEmpty())
            evaluated.add(stack.pop())
        return evaluated
    } catch (e: Exception) {
        println("Invalid expression")
        throw e
    }

}