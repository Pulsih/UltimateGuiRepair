package me.pulsi_.ultimateguirepair.listeners;

import me.pulsi_.ultimateguirepair.configs.Values;
import me.pulsi_.ultimateguirepair.gui.GuiHolder;
import me.pulsi_.ultimateguirepair.managers.MessageManager;
import me.pulsi_.ultimateguirepair.utils.Methods;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlockInteraction implements Listener {

    /**
     * Simple listener to check if a player clicks on an anvil.
     */
    @EventHandler
    public void noAnvilClick(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || block.getType() != Material.ANVIL) return;
        e.setCancelled(true);

        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();
        if (item.getAmount() > 1 && Values.CONFIG().isSingularRepair()) {
            MessageManager.singularRepair(p);
            return;
        }

        if (item.getType().getMaxDurability() <= 0) {
            MessageManager.cannotRepair(p);
            return;
        }

        if (item.getDurability() < 1) {
            MessageManager.alreadyRepaired(p);
            return;
        }

        GuiHolder.getInstance().openBank(p);

        if (Values.CONFIG().isItemOnAnvilEffect() && item.getType() != Material.AIR) Methods.fireItemAnimation(p, block.getLocation());
    }
}