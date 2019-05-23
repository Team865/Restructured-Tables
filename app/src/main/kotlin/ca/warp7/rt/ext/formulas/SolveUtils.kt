import ca.warp7.rt.ext.formulas.token.*

fun Expr.solve(): Double {
    var expr = this

    while (expr.size > 1) {
        var i = 0
        var curSubexpr: MutableExpr = mutableListOf()
        for (it in expr.withIndex()) {
            if (it.value == Ch('(')) {
                i = it.index
                curSubexpr = mutableListOf()
            } else if (it.value == Ch(')')) {
                break
            } else {
                curSubexpr.add(it.value)
            }
        }
        expr = expr.replaceRange(i..(i + curSubexpr.size), listOf(Num(curSubexpr.resolveBEDMAS())))
    }
    return expr.resolveBEDMAS()
}

fun Expr.resolveBEDMAS(): Double {
    var expr = this
    expr = expr.resolveOperators(Operator.Modulo)
    expr = expr.resolveOperators(Operator.Power)
    expr = expr.resolveOperators(Operator.Root)
    expr = expr.resolveOperators(Operator.Times, Operator.DividedBy)
    expr = expr.resolveOperators(Operator.Plus, Operator.Minus)
    expr = expr.resolveOperators(Operator.Lt, Operator.Equals, Operator.Gt)
    expr = expr.resolveOperators(Operator.And)
    expr = expr.resolveOperators(Operator.Or)
    return (expr[0] as Num).num
}

fun Expr.resolveOperators(vararg operators: Operator): Expr {
    var expr = this
    val ops = operators.toList()

    val opsLeft = ops.filter { it in expr }.toMutableList()
    while (opsLeft.isNotEmpty()) {
        expr = expr.operateAt(opsLeft.map { expr.indexOf(it) }.min()!!)
        opsLeft.removeIf { it !in expr }
    }
    return expr
}

private fun Expr.operateAt(i: Int): Expr {
    val op = this[i] as Operator
    val v = (op).execute((this[i - 1] as Num).num, (this[i + 1] as Num).num)
    return this.slice(0..(i - 2)) + listOf(Num(v)) + this.slice((i + 2)..(this.size - 1))
}