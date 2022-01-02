package me.pulsi_.ultimateguirepair.listeners;

import me.pulsi_.ultimateguirepair.utils.MapUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener {

    /**
     * Avoid loosing an item in case of crash / kick / ban.
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (MapUtils.hasItemOnAnvil(p)) MapUtils.removeItemOnAnvil(e.getPlayer());
    }
}
