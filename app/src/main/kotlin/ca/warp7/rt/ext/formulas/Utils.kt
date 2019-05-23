fun <E> List<E>.replaceRange(intRange: IntRange, that: List<E>) =
        this[0 until intRange.first] + that + this[(intRange.last + 1) until this.size]

operator fun <E> List<E>.get(range: IntRange) = slice(range)

fun <E> List<E>.replaceAt(i: Int, that: List<E>): List<E> =
        this[0 until i] + that + this[(i + 1) until this.size]

fun <E> List<E>.split(by: E): List<List<E>> {
    val indices = listOf(-1) + (0..(size - 1)).filter { this[it] == by }
    return indices.mapIndexed { i, it ->
        if (i + 1 < indices.size) this[it + 1 until indices[i + 1]]
        else slice(it + 1 until size)
    }
}

fun <E> List<E>.replaceAll(e: E, that: List<E>): List<E> {
    if (e in that) throw java.lang.IllegalArgumentException("e in that")
    var l = this

    (0 until size)
            .filter { this[it] == e }
            .reversed()
            .forEach { l = l.replaceAt(it, that) }

    return l
}

fun Boolean.toDouble() = when (this) {
    true -> 1.0
    false -> 0.0
}