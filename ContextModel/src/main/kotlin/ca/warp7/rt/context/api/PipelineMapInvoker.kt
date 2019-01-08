package ca.warp7.rt.context.api

interface PipelineMapInvoker {
    fun with(vararg mappedColumns: MappedColumn): PipelineMetricStream
}