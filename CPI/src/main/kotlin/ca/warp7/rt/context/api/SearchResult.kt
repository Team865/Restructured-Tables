package ca.warp7.rt.context.api

data class SearchResult(
        val title: String,
        val header: String,
        val summary: Map<String, String>,
        val items: List<Int>,
        val status: String,
        val statusFlavour: Int,
        val actions: List<Int>
)