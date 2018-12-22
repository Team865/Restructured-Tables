package ca.warp7.rt.core.env

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object EnvUser {

    private val userConfigFile: File
        get() {
            val f = File(EnvUtils.appHome, "/user.json")
            f.createNewFile()
            return f
        }

    private val config: JsonObject

    init {
        val s = userConfigFile.readText().trim()
        config = if (s.isNotEmpty()) {
            Parser().parse(StringBuilder(s)) as JsonObject
        } else JsonObject()
    }

    fun get(name: String, defaultValue: String): String {
        return config.string(name) ?: defaultValue
    }

    fun get(name: String, defaultValue: Int): Int {
        return config.int(name) ?: defaultValue
    }

    fun get(name: String, defaultValue: Double): Double {
        return config.double(name) ?: defaultValue
    }

    fun set(name: String, value: Any) {
        config[name] = value
    }

    fun save() {
        config["env.lastSaved"] = SimpleDateFormat("dd/MM/yy hh:mm:ss").format(Date())
        userConfigFile.writeText(config.toJsonString(prettyPrint = true))
    }
}