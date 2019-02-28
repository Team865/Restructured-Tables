package ca.warp7.rt.api

interface PipelineMapInvoker {
    fun with(vararg mappedColumns: MappedColumn): PipelineMetricStream
}