package ca.warp7.rt.context

/**
 * Adapters contribute to the actual values of the data using either existing values in
 * the Tables or from another source of input. They can modify the Tables in various ways
 * and is the real "analysis" component of this model. A Context with a data source that
 * does not use Tables always has a delegated adapter to convert the format so that data
 * can be merged together
 */
interface ContextAdapter1 {
    fun update(contextReference: ContextReference, pipeline: ContextPipeline)
}