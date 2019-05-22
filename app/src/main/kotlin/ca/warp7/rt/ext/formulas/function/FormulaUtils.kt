package ca.warp7.rt.ext.formulas.function

import ca.warp7.rt.ext.formulas.token.Ch
import ca.warp7.rt.ext.formulas.token.Name
import ca.warp7.rt.ext.formulas.token.tokenize
import split


fun def(rawExpr: String): Pair<String, Formula> {
    val expr = rawExpr.tokenize()
    val funcName = (expr[0] as Name).name
    val end = expr.indexOf(Ch(')'))
    val funcVars = expr.slice(2 until end)
            .split(Ch(','))
            .map { it[0] as Name }

    return funcName to CustomFormula(expr.slice((end + 2) until expr.size), funcVars)
}

fun main(){
    println(def("max(a, b): a*(a>b | a=b) + b*(b>a)"))
}
