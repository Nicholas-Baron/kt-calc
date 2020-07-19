sealed class Token

// Classes which carry data
data class Floating(val value: Float) : Token()
data class Integer(val value: Int) : Token()

//singleton
object LeftParenthesis : Token()
object RightParenthesis : Token()

// Binary operations
sealed class BinaryOp(): Token()

class BitAnd : BinaryOp()
class BitOr : BinaryOp()
class Division(val divide: String="/") : BinaryOp()
class Exponentiation : BinaryOp()
class LeftShift : BinaryOp()
class Minus : BinaryOp()
class Modulo : BinaryOp()
class Multiply(val mult:String="*") : BinaryOp()
class Plus(val plusSymbol: String="+") : BinaryOp()
class Remainder : BinaryOp()
class RightShift : BinaryOp()

// Functions
sealed class Function: Token()

class AbsVal : Function()
class Cosine : Function()
class Sine : Function()
class SquareRoot : Function()
class Tangent : Function()