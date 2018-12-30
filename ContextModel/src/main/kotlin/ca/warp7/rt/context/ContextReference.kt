package ca.warp7.rt.context

/**
 * The Context of a set of data is the scope and state in which the data is relevant for a
 * specific analysis. For example, to find out how some teams are performing at a specific event,
 * the context would be that event, whereas to find out how they performed in the entire season,
 * the context would be the year. Contexts can be defined by many metrics (there is a list below).
 * For example, they could be analyzing a district, an alliance, a set of scouts, etc. A Context
 * will always have a Data Source, a Version, and a delegated Adapter, which are the key to
 * integrating different sets of data
 *
 * A Context must:
 * Provide a set of metrics with their contextual values
 * Provide a comparison function with any other Context
 * Provide the set of Tables with their respective metrics managed by this Context
 * lookup an return a Table based on a set of arbitrary metrics
 * Be able to determine if an up-cast/downcast operation is possible
 * Create a Downcast Context based on table metrics
 * Accept an Adapter to up-cast from other Contexts
 * Retain and manage a set of Pipelines connected to this Context
 * Provide an interface for Pipelines to cache their data
 * Manage configurations for the Clients of this Context
 * Manage a set of Context Loaders
 */
interface ContextReference {
    val inputPipeline: ContextPipeline
    val outputPipeline: ContextPipeline
    val loader: ContextLoader
    val metrics: MetricsSet
    val clients: List<ContextClient>
}