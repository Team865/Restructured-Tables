package ca.warp7.rt.context.api

data class SearchResult(
        val title: String,
        val header: String = "",
        val summary: Map<String, String> = mapOf(),
        val actionItems: List<SearchActionItem> = listOf(),
        val actionButtons: List<SearchActionButton> = listOf(),
        val status: SearchStatus? = null
)