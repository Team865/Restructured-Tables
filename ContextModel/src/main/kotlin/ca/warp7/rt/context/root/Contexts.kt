package ca.warp7.rt.context.root

import ca.warp7.rt.context.ContextRoot

@Suppress("unused")
object Contexts {

    private var root: ContextRoot? = null

    /**
     * Loads the singleton Context Root for the application with command line arguments
     */
    fun loadRoot(args: Array<String>?) = root ?: loadRootImpl(args ?: arrayOf()).apply { root = this }
}