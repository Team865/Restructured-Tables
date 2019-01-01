@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ca.warp7.rt.context

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object Metadata {

    private val userConfigFile: File = File(File(System.getProperty("user.home")), "/warp7.meta.json")
            .apply { createNewFile() }

    val data: JsonObject = userConfigFile.readText().trim().run {
        if (isNotEmpty()) Parser().parse(java.lang.StringBuilder(this)) as JsonObject
        else JsonObject()
    }

    fun save() {
        data[MetaKey.lastSaved] = SimpleDateFormat("dd/MM/yy hh:mm:ss").format(Date())
        userConfigFile.writeText(data.toJsonString(prettyPrint = true))
    }
}