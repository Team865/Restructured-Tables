package ca.warp7.rt.context

interface ContextPlugin {
    val contextLoaders: Array<ContextLoader>
}