package ca.warp7.rt.ext.formulas.function

import ca.warp7.rt.ext.formulas.token.Expr
import ca.warp7.rt.ext.formulas.token.Num
import solve

class FunctionFormula(val f: (List<Double>) -> Double) : Formula() {
    override fun solve(args: List<Expr>): Expr {
        return listOf(Num(f(args.map { it.solve() })))
    }
}
