sealed class Token

// Classes which carry data
data class Floating(val value: Float) : Token()
data class Integer(val value: Int) : Token()

object LeftParenthesis : Token()
object RightParenthesis : Token()

// Binary operations
sealed class BinaryOp : Token()
object BitAnd : BinaryOp()
object BitOr : BinaryOp()
object Division : BinaryOp()
object Exponentiation : BinaryOp()
object LeftShift : BinaryOp()
object Minus : BinaryOp()
object Modulo : BinaryOp()
object Multiply : BinaryOp()
object Plus : BinaryOp()
object Remainder : BinaryOp()
object RightShift : BinaryOp()

// Functions
sealed class Function : Token()
object AbsVal : Function()
object Cosine : Function()
object Sine : Function()
object SquareRoot : Function()
object Tangent : Function()
