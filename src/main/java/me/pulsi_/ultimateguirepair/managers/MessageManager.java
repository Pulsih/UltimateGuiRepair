package me.pulsi_.ultimateguirepair.managers;

import me.pulsi_.ultimateguirepair.configs.Values;
import me.pulsi_.ultimateguirepair.utils.ChatUtils;
import me.pulsi_.ultimateguirepair.utils.Methods;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {

    public static void noPermission(CommandSender s) {
        String message = Values.MESSAGES().getNoPermission();
        if (message != null) s.sendMessage(addPrefix(message));
    }

    public static void pluginReloaded(CommandSender s) {
        String message = Values.MESSAGES().getPluginReloaded();
        if (message != null) s.sendMessage(addPrefix(message));
    }

    public static void alreadyRepaired(Player p) {
        String message = Values.MESSAGES().getAlreadyRepaired();
        if (message != null) p.sendMessage(addPrefix(message));
    }

    public static void insufficientMoney(Player p, double remainingMoney) {
        String message = Values.MESSAGES().getInsufficientMoney();
        if (message != null) p.sendMessage(addPrefix(message
                .replace("%remaining_money_formatted%", Methods.format(remainingMoney))
                .replace("%remaining_money%", "" + remainingMoney)
                ));
    }

    public static void singularRepair(Player p) {
        String message = Values.MESSAGES().getSingularRepair();
        if (message != null) p.sendMessage(addPrefix(message));
    }

    public static void successfullyRepaired(Player p, double cost) {
        String message = Values.MESSAGES().getSuccessfullyRepaired();
        if (message != null) p.sendMessage(addPrefix(message
                .replace("%cost_formatted%", Methods.format(cost))
                .replace("%cost%", "" + cost)
        ));
    }

    private static String addPrefix(String message) {
        String prefix = Values.CONFIG().getPrefix();
        if (prefix == null) return ChatUtils.color(message);
        return ChatUtils.color(prefix + message);
    }
}