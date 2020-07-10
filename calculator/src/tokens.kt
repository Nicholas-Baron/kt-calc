sealed class Token

// Classes which carry data
data class Floating(val value: Float) : Token()
data class Integer(val value: Int) : Token()

object LeftParenthesis : Token()
object RightParenthesis : Token()

// Binary operations
object BitAnd : Token()
object BitOr : Token()
object Division : Token()
object Exponentiation : Token()
object LeftShift : Token()
object Minus : Token()
object Modulo : Token()
object Multiply : Token()
object Plus : Token()
object Remainder : Token()
object RightShift : Token()

// Functions
object AbsVal : Token()
object Cosine : Token()
object Sine : Token()
object SquareRoot : Token()
object Tangent : Token()