@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ca.warp7.rt.context

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object AppMetadata {

    private val userConfigFile: File
        get() = File(File(System.getProperty("user.home")), "/warp7metadata.json").apply { createNewFile() }

    val data: JsonObject

    init {
        val s = userConfigFile.readText().trim()
        data = if (s.isNotEmpty()) {
            Parser().parse(StringBuilder(s)) as JsonObject
        } else JsonObject()
    }

    fun save() {
        data["env.lastSaved"] = SimpleDateFormat("dd/MM/yy hh:mm:ss").format(Date())
        userConfigFile.writeText(data.toJsonString(prettyPrint = true))
    }
}
