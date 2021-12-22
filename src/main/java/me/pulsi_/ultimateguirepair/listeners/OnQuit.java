package me.pulsi_.ultimateguirepair.listeners;

import me.pulsi_.ultimateguirepair.utils.MapUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class OnQuit implements Listener {

    /**
     * Avoid loosing an item in case of crash / kick / ban.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (!MapUtils.hasItemQueued(p)) return;
        ItemStack itemOnAnvilRepaired = MapUtils.getPlayerItemQueued(p);
        p.setItemInHand(itemOnAnvilRepaired);

        MapUtils.removeItemOnAnvil(p);
        MapUtils.removeItemQueued(p);
    }
}
