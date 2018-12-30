package ca.warp7.rt.context

interface PipelineMapInvoker {
    operator fun invoke(vararg mappedColumns: MappedColumn): PipelineStream
}