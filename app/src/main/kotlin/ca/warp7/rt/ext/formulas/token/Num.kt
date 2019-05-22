package ca.warp7.rt.ext.formulas.token

data class Num(val num: Double) : Token {
    companion object {
        val regex = "[0-9]+\\.?[0-9]*".toRegex()
    }
}