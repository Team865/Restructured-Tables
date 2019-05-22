package ca.warp7.rt.ext.formulas.token

data class Name(val name: String) : Token {
    enum class NameType { Func, Cell, Col, Table }

    val nameType = when {
        name.matches("[A-Za-z_]+".toRegex()) -> NameType.Func
        name.matches("'.+?'".toRegex()) -> NameType.Cell
        name.matches("\\[.+?]".toRegex()) -> NameType.Col
        name.matches("\\{.*?}".toRegex()) -> NameType.Table
        else -> throw IllegalArgumentException("the name must match the regex")
    }

    companion object {
        val regex = "[A-Za-z_]+|'.+?'|\\[.?]|\\{.*?}".toRegex()
    }
}