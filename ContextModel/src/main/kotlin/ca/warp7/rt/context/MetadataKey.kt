package ca.warp7.rt.context

enum class MetadataKey(val key: String) {
    LastSaved("lastSaved"),
    AppVersion("appVersion"),
    Plugins("plugins"),
    ContextPath("contextPath"),
    UserName("userName"),
    DeviceNAme("deviceName"),
    LastOpenedPlugin("lastOpenedPlugin")
}