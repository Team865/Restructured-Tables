package ca.warp7.rt.context

object SingletonPipeline : Pipeline {
    override val name: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val adapter: ContextAdapter
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val monitor: ContextMonitor
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun lastUpdated(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun open() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun merge(other: Pipeline) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> convert(receiver: ContextReceiver<T>): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}