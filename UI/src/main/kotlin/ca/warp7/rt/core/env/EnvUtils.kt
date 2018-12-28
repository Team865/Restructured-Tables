@file:Suppress("unused")

package ca.warp7.rt.core.env

import java.io.File

object EnvUtils {

    val user: String
        get() = System.getProperty("user_.name")

    private val userHome: String
        get() = System.getProperty("user_.home")

    val appHome: File
        get() {
            val f = File("$userHome/.warp7")
            if (!f.exists()) f.mkdir()
            return f
        }

    val computerName: String
        get() {
            val env = System.getenv()
            return if (env.containsKey("COMPUTERNAME"))
                env["COMPUTERNAME"] ?: "Unknown Computer"
            else
                env.getOrDefault("HOSTNAME", "Unknown Computer")
        }
}

fun ensureWindowsOS() {
    if (!System.getProperty("os.name").toLowerCase().startsWith("win"))
        throw RuntimeException("Only Windows is supported")
}