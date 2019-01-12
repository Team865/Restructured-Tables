package ca.warp7.rt.context.api

class StringMetric(name: String, validator: (String) -> Boolean = { true }) : BaseMetric<String>(name, validator)