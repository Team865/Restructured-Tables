package ca.warp7.rt.context.api

import ca.warp7.rt.context.api.BaseMetric

class StringMetric(name: String, validator: (String) -> Boolean = { true }) : BaseMetric<String>(name, validator)