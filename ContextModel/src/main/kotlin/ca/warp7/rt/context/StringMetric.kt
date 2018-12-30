package ca.warp7.rt.context

class StringMetric(name: String, validator: (String) -> Boolean = { true }) : Metric<String>(name, validator)