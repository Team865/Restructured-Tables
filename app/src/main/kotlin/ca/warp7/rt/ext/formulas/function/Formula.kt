package ca.warp7.rt.ext.formulas.function

import ca.warp7.rt.ext.formulas.token.Expr
import ca.warp7.rt.ext.formulas.token.Name

abstract class Formula(open val inputArgs: List<Name>) {
    abstract fun solve(args: List<Expr>): Expr
}