package ca.warp7.rt.context

/**
 * Context pipelines provide the ability to transfer data via some type of streaming.
 * For example, a pipeline for The Blue Alliance is responsible for managing the API
 * key and make requests to the server to fetch the data. Another one might be used to
 * store the current data into an USB key. A pipeline takes as an input of what to send
 * or to fetch and then performs the operation on request based on the interface of the
 * target Context. The result is then cached in the Stash of the Context and available
 * for an Adapter to use. A Pipeline is always single-directional. They can either fetch
 * data from the upstream or push data to the downstream. Pipelines are essential for
 * the system to be decentralized
 */
interface Pipeline {
    fun addAdapter(name: String, adapter: ContextAdapter)
}