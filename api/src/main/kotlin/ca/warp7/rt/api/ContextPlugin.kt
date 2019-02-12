package ca.warp7.rt.api

interface ContextPlugin {
    val contextLoaders: Array<ContextLoader>
    val features: Array<Feature>
}