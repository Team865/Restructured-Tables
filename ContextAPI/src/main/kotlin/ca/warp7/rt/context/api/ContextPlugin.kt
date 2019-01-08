package ca.warp7.rt.context.api

interface ContextPlugin {
    val contextLoaders: Array<ContextLoader>
    val features: Array<Feature>
}