package ca.warp7.rt.api

/**
 *
 * Loaders determine for the user whether there are some other Contexts available for use upon request.
 * When they do, they are able to return a pipeline and adapter for those Context. For example, loaders can
 * check if there is an USB inserted into the computer that has some data on it, or if The Blue Alliance
 * is accessible. It would then create and return the Pipeline to that Context.
 *
 * A Loader must:
 * Belong to a Context and holds a reference to it
 * Use the Context's metrics and tables to determine what data the Context might want
 * Check the availability of those data by converting the requested data in the target format
 * Be able to return a Pipeline based on the requested data
 */
interface ContextRoot : ContextLoader {
    val default: Context?
    val active: Context?
}