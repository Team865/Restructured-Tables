package ca.warp7.rt.ext.formulas.function

import ca.warp7.rt.ext.formulas.token.Expr

abstract class Formula{
    abstract fun solve(args: List<Expr>): Expr
}