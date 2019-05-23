package ca.warp7.rt.ext.formulas

import ca.warp7.rt.ext.formulas.function.Formula
import ca.warp7.rt.ext.formulas.function.FunctionFormula
import ca.warp7.rt.ext.formulas.function.toDouble
import ca.warp7.rt.ext.formulas.token.*
import krangl.DataCol
import krangl.DataFrame
import krangl.dataFrameOf
import replaceAt
import replaceRange
import resolveBEDMAS
import split

class ExpressionEnvironment(val curDf: String, var dfs: MutableMap<String, DataFrame>) {
    private val vars: MutableMap<String, Any> = mutableMapOf()
    private val funcs: MutableMap<String, Formula> = mapOf(
            "len" to FunctionFormula { it.size.toDouble() },
            "sum" to FunctionFormula { it.sum() },
            "max" to FunctionFormula { it.max() ?: 0.0 },
            "min" to FunctionFormula { it.min() ?: 0.0 },
            "ave" to FunctionFormula { it.average() },
            // TODO median
            // TODO mode
            "bool" to FunctionFormula { (it[0] > 0.0).toDouble() },
            "true" to FunctionFormula { (it[0] > 0.0).toDouble() },
            "false" to FunctionFormula { (it[0] <= 0.0).toDouble() },
            "if" to FunctionFormula { if (it[0] > 0.0) it[1] else it[2] },
            "get" to FunctionFormula { it[1 + it[0].toInt()] }
    ).toMutableMap()

    private fun resolve(initExpr: Expr): Any {
        var expr = initExpr

        expr.forEachIndexed { i, it ->
            if (it in vars.keys.map { Name(it) }) expr = expr.replaceAt(i, listOf((vars[(it as Name).name]!!).toToken()))
        }
        while (expr.size > 1) {
            var i = 0
            var curSubexpr: MutableExpr = mutableListOf()
            var funcName: Token? = null
            var expectingBracket = false
            for (it in expr.withIndex()) {
                if (it.value is Name) {
                    i = it.index
                    funcName = it.value
                    expectingBracket = true
                    curSubexpr = mutableListOf()
                } else if (it.value == Ch('(')) {
                    if (!expectingBracket) {
                        funcName = null
                        i = it.index
                        curSubexpr = mutableListOf()
                    } else expectingBracket = false
                } else if (it.value == Ch(')')) {
                    break
                } else {
                    curSubexpr.add(it.value)
                }
            }
            expr = when (funcName) {
                null -> expr.replaceRange(
                        i..(i + curSubexpr.size + 1),
                        listOf(Num(curSubexpr.resolveBEDMAS()))
                )
                else -> {
                    expr.replaceRange(
                            i..(i + 2 + curSubexpr.size),
                            funcs[(funcName as Name).name]!!.solve(curSubexpr.split(Ch(',')))
                    )
                }
            }
        }

        return expr.resolveBEDMAS()
    }

    fun resolve(exprRaw: String) = resolve(exprRaw.tokenize())

    fun resolveCol(exprRaw: String): List<Any> {
        val expr = exprRaw.tokenize()

        val refs = expr.filter { it is Name && it.nameType == Name.NameType.Cell }
        return dfs.getValue(curDf).rows.map { row ->
            refs.forEach {
                val n = it as Name
                vars[n.name] = row[n.name.substring(1 until n.name.length - 1)]!!
            }
            resolve(expr)
        }
    }

}

private fun Any.toToken(): Token {
    return when (this) {
        is String -> toToken()
        is Char -> toString().toToken()
        is Number -> Num(this.toDouble())
        else -> throw NotImplementedError()
    }
}

fun main() {
    val env = ExpressionEnvironment("this", mapOf("this" to
            dataFrameOf(
                    "hatch", "cargo")(
                    1, 2,
                    2, 3,
                    3, 0)
    ).toMutableMap())

    val exprRaw = readLine()!!
    val newCol = env.resolveCol(exprRaw)
    env.dfs["this"] = env.dfs["this"]!! + dataFrameOf(exprRaw)(*newCol.toTypedArray())
    println(env.dfs["this"])
    val exprRaw2 = readLine()!!
    val newCol2 = env.resolveCol(exprRaw2)
    env.dfs["this"] = env.dfs["this"]!! + dataFrameOf(exprRaw2)(*newCol2.toTypedArray())
    println(env.dfs["this"])
}

operator fun DataFrame.plus(that: DataFrame): DataFrame =
        dataFrameOf(*(this.cols + that.cols).toTypedArray())

operator fun DataFrame.plus(that: DataCol): DataFrame =
        dataFrameOf(*(this.cols + that).toTypedArray())
