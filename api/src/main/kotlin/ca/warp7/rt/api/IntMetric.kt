package ca.warp7.rt.api

class IntMetric(name: String, validator: (Int) -> Boolean = { true }) : BaseMetric<Int>(name, validator)