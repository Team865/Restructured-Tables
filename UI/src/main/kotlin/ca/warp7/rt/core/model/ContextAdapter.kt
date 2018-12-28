package ca.warp7.rt.core.model

interface ContextAdapter {
    fun update(source: Context, destination: Context)
}