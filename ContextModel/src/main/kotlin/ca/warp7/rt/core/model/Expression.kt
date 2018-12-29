package ca.warp7.rt.core.model


infix fun String.to(that: (PipelineExpression) -> Any?) = Pair(this, that)

val PipelineExpression.matchNumber get() = this.getMetric(matchNumber_)