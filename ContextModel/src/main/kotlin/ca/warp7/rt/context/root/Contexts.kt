package ca.warp7.rt.context.root

import ca.warp7.rt.context.ContextRoot

@Suppress("unused")
object Contexts {

    private var root1: ContextRoot? = null

    var args: Array<String>? = null

    private val root get() = root1 ?: loadRoot(args ?: arrayOf()).also { root1 = it }

    /**
     * Holds the metadata of the root context
     */
    val metadata get() = root.data
}