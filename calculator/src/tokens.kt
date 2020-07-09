sealed class Token

// Classes which carry data
data class Floating(val value: Float) : Token()
data class Integer(val value: Int) : Token()

// Binary operations
class BitAnd : Token()
class BitOr : Token()
class Division : Token()
class Exponentiation : Token()
class LeftShift : Token()
class Minus : Token()
class Modulo : Token()
class Multiply : Token()
class Plus : Token()
class Remainder : Token()
class RightShift : Token()

// Functions
class AbsVal : Token()
class Cosine : Token()
class Sine : Token()
class SquareRoot : Token()
class Tangent : Token()