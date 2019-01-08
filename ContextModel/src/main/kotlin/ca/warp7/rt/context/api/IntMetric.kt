package ca.warp7.rt.context.api

import ca.warp7.rt.context.api.BaseMetric

class IntMetric(name: String, validator: (Int) -> Boolean = { true }) : BaseMetric<Int>(name, validator)