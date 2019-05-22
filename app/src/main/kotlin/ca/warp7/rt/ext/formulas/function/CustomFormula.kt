package ca.warp7.rt.ext.formulas.function

import ca.warp7.rt.ext.formulas.token.Ch
import ca.warp7.rt.ext.formulas.token.Expr
import ca.warp7.rt.ext.formulas.token.Name
import ca.warp7.rt.ext.formulas.token.Num
import replaceAll
import solve


data class CustomFormula(val expr: Expr, override val inputArgs: List<Name>) : Formula(inputArgs) {
    override fun solve(args: List<Expr>): Expr {
        if (inputArgs.size != args.size) throw IllegalArgumentException("Incorrect number of arguments")

        val map: MutableMap<Name, Expr> = inputArgs.zip(args).toMap().toMutableMap()
        var solution = expr
        map.forEach { solution = solution.replaceAll(it.key, listOf(Num(it.value.solve()!!))) }

        return listOf(Ch('(')) + solution + listOf(Ch(')'))
    }
}