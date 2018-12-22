@file:Suppress("unused")

package ca.warp7.rt.core.env

object EnvUtils {

    @JvmStatic
    val user: String
        get() = System.getProperty("user.name")

    @JvmStatic
    val userHome: String
        get() = System.getProperty("user.home")

    @JvmStatic
    val computerName: String?
        get() {
            val env = System.getenv()
            return if (env.containsKey("COMPUTERNAME"))
                env["COMPUTERNAME"]
            else
                env.getOrDefault("HOSTNAME", "Unknown Computer")
        }

    @JvmStatic
    fun ensureWindowsOS() {
        if (!System.getProperty("os.name").toLowerCase().startsWith("win"))
            throw RuntimeException("Only Windows is supported")
    }
}
