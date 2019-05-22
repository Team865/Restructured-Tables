package ca.warp7.rt.ext.formulas.token

enum class Operator : Token {
    Plus, Minus,
    Times, DividedBy, Modulo,
    Power, Root,
    Gt, Equals, Lt,
    And, Or;

    companion object {
        val map = mapOf(
            '+' to Plus, '-' to Minus,
            '*' to Times, '/' to DividedBy, '%' to Modulo,
            '^' to Power, '√' to Root,
            '>' to Gt, '=' to Equals, '<' to Lt,
            '&' to And, '|' to Or
        )
        val regex= ("[" +map.keys.joinToString (prefix = "\\",separator = "\\")+"]").toRegex()
    }

    fun execute(a: Double, b: Double): Double {
        return when (this) {
            Plus -> a + b
            Minus -> a - b
            Times -> a * b
            DividedBy -> a / b
            Modulo -> a % b
            Power -> Math.pow(a, b)
            Root -> {
                when (a) {
                    2.0 -> Math.sqrt(b)
                    3.0 -> Math.cbrt(b)
                    else -> {
                        val factor = 1.0 / a // 4^0.5 = 2 root 4, 8^0.3333... = 3 root 8
                        Math.pow(b, factor)
                    }
                }
            }
            Gt -> if (a > b) 1.0 else 0.0
            Lt -> if (a < b) 1.0 else 0.0
            Equals -> if (a == b) 1.0 else 0.0
            And -> if ((a > 0.0) && (b > 0.0)) 1.0 else 0.0
            Or -> if ((a > 0.0) || (b > 0.0)) 1.0 else 0.0
        }
    }
}