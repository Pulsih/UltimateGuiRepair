package me.pulsi_.ultimateguirepair.utils;

import me.pulsi_.ultimateguirepair.configs.Values;

public class UGRLogger {

    public static void logError(String error) {
        ChatUtils.consoleMessage(Values.CONFIG().getPrefix() + "&c[ERROR] " + error);
    }

    public static void logWarn(String error) {
        ChatUtils.consoleMessage(Values.CONFIG().getPrefix() + "&e[WARN] " + error);
    }

    public static void logInfo(String error) {
        ChatUtils.consoleMessage(Values.CONFIG().getPrefix() + "&9[INFO] " + error);
    }
}