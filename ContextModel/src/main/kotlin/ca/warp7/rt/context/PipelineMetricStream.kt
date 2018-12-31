package ca.warp7.rt.context

interface PipelineMetricStream {
    fun mapPure(vararg mappedColumns: MappedColumn) = this
    fun mapFrom(metrics: MetricsSet? = null): PipelineMapInvoker
    fun mapLast(vararg mappedColumns: MappedColumn)
}