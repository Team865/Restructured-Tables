@file:Suppress("unused")

package ca.warp7.rt.core.env

import java.io.File

object EnvUtils {

    @JvmStatic
    val user: String
        get() = System.getProperty("user.name")

    @JvmStatic
    val userHome: String
        get() = System.getProperty("user.home")

    @JvmStatic
    val appHome: File
        get() {
            val f = File("$userHome/.warp7")
            if (!f.exists()) f.mkdir()
            return f
        }

    @JvmStatic
    val computerName: String
        get() {
            val env = System.getenv()
            return if (env.containsKey("COMPUTERNAME"))
                env["COMPUTERNAME"] ?: "Unknown Computer"
            else
                env.getOrDefault("HOSTNAME", "Unknown Computer")
        }

    @JvmStatic
    fun ensureWindowsOS() {
        if (!System.getProperty("os.name").toLowerCase().startsWith("win"))
            throw RuntimeException("Only Windows is supported")
    }
}
