package ca.warp7.rt.context.api

class IntMetric(name: String, validator: (Int) -> Boolean = { true }) : BaseMetric<Int>(name, validator)