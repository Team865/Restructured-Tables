//@file:Suppress("unused")

package ca.warp7.rt.core.env

import java.io.File

object EnvUtils {

    val user: String
        get() = System.getProperty("user.name")

    private val userHome: String
        get() = System.getProperty("user.home")

    val appHome: File
        get() {
            val f = File("$userHome/.warp7")
            if (!f.exists()) f.mkdir()
            return f
        }
}