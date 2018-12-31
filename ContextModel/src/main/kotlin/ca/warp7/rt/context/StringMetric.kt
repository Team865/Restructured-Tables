package ca.warp7.rt.context

class StringMetric(name: String, validator: (String) -> Boolean = { true }) : BaseMetric<String>(name, validator)