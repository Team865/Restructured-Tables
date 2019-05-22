package ca.warp7.rt.ext.formulas.token

typealias Expr = List<Token>
typealias MutableExpr = MutableList<Token>

fun String.toToken(): Token? {
    return when {
        matches(Name.regex) -> Name(this)
        matches(Num.regex) -> Num(this.toDouble())
        matches(Operator.regex) -> Operator.map[this[0]]
        this.trim() == "" -> null
        else -> Ch(this[0])
    }
}

fun String.tokenize(): Expr {
    val tokens = mutableListOf<String>()

    val names = Name.regex.findAll(this).map { it.range }.toList()
    val noNames = this
    names.forEach { intRange ->
        noNames.replaceRange(
            intRange.first,
            intRange.last,
            "A".repeat(intRange.last - intRange.first + 1)
        )
    }
    val nums = Num.regex.findAll(this).map { it.range }.toList()
    val a = names + nums

    var i = 0
    while (i < length) {
        val ins = a.map { i in it }
        if (true !in ins) {
            tokens += get(i).toString()
            i++
        } else {
            val intRange = a[ins.indexOf(true)]
            tokens += substring(intRange)
            i += intRange.last - intRange.first + 1
        }
    }
    return tokens.mapNotNull { it.toToken() }
}

