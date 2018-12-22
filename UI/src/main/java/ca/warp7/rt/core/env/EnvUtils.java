package ca.warp7.rt.core.env;

import java.util.Map;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
public class EnvUtils {
    public static void ensureWindowsOS() {
        if (!System.getProperty("os.name").toLowerCase().startsWith("win"))
            throw new RuntimeException("Only Windows is supported");
    }

    public static String getUser() {
        return System.getProperty("user.name");
    }

    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    public static String getComputerName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME"))
            return env.get("COMPUTERNAME");
        else return env.getOrDefault("HOSTNAME", "Unknown Computer");
    }

}
