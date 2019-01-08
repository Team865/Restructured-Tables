package ca.warp7.rt.context.model

import ca.warp7.rt.context.api.ContextRoot

@Suppress("unused")
object Contexts {

    private var root1: ContextRoot? = null

    var args: Array<String>? = null

    /**
     * Get the default context root
     */
    val root
        get() = root1
                ?: loadRoot(args
                        ?: arrayOf()).also { root1 = it }

    /**
     * Holds the metadata of the root context
     */
    val metadata get() = root.data

    /**
     * Get the current context
     */
    val current get() = root.active
}