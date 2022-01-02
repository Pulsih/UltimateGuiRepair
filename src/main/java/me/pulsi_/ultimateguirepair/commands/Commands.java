package me.pulsi_.ultimateguirepair.commands;

import me.pulsi_.ultimateguirepair.UltimateGuiRepair;
import me.pulsi_.ultimateguirepair.managers.DataManager;
import me.pulsi_.ultimateguirepair.managers.MessageManager;
import me.pulsi_.ultimateguirepair.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor, TabCompleter {

    private final UltimateGuiRepair plugin;

    public Commands(UltimateGuiRepair plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if (args.length == 0) {
            if (s.isOp() || s.hasPermission("ultimateguirepair.reload")) {
                s.sendMessage(ChatUtils.color("&8[&a&lUltimate&c&lGui&9&lRepair&8] &7Running on v" + plugin.getDescription().getVersion() + ", made by Pulsi_"));
                s.sendMessage(ChatUtils.color("&7Type /ugr reload to reload the plugin."));
                return false;
            }
            s.sendMessage("&8[&a&lUltimate&c&lGui&9&lRepair&8] &7Running on v" + plugin.getDescription().getVersion() + ", made by Pulsi_");
            s.sendMessage(ChatUtils.color("&cYou do not have access to any sub-command."));
            return false;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!s.hasPermission("ultimateguirepair.reload")) {
                MessageManager.noPermission(s);
                return false;
            }
            DataManager.getInstance().reloadPlugin();
            MessageManager.pluginReloaded(s);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String alias, String[] args) {

        List<String> args0 = new ArrayList<>();
        if (s.hasPermission("ultimateguirepair.reload")) args0.add("reload");

        List<String> resultArgs0 = new ArrayList<>();
        if (args.length == 1) {
            for (String a : args0)
                if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                    resultArgs0.add(a);
            return resultArgs0;
        }

        return null;
    }
}
