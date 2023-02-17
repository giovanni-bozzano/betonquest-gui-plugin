package com.giovannibozzano.betonquestgui;

import org.betonquest.betonquest.utils.PlayerConverter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.betonquest.betonquest.api.QuestCompassTargetChangeEvent;


public class EventListener implements Listener {

    @EventHandler
    public void QuestCompassTargetChangeEventListener(QuestCompassTargetChangeEvent event)
    {
        Player player = event.getProfile().getOnlineProfile().get().getPlayer();
        if(event.getLocation().getWorld() == player.getWorld()) {
            PacketHandler.sendPacketTargetLocation(player, event.getLocation());
        }
    }
}
