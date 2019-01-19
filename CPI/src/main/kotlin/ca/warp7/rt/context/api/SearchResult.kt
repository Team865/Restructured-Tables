package ca.warp7.rt.context.api

data class SearchResult(
        val title: String,
        val header: String,
        val summary: Map<String, String>,
        val actionItems: List<SearchActionItem>,
        val actionButtons: List<SearchActionButton>,
        val status: SearchStatus? = null
)