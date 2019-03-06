package ca.warp7.rt.ext.humber

import java.io.File

@Suppress("MemberVisibilityCanBePrivate")
object Humber {
    val root = File(System.getProperty("user.home") + "/Desktop/HumberScouting")

    init {
        root.mkdirs()
    }

    val raw = File(root, "raw")

    init {
        raw.mkdir()
    }

    val json = File(root, "json")

    init {
        json.mkdir()
    }
}