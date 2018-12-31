package ca.warp7.rt.context

interface PipelineMapInvoker {
    fun with(vararg mappedColumns: MappedColumn): PipelineMetricStream
}