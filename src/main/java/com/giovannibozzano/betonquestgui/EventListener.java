package com.giovannibozzano.betonquestgui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.betoncraft.betonquest.api.QuestCompassTargetChangeEvent;


public class EventListener implements Listener {

    @EventHandler
    public void QuestCompassTargetChangeEventListener(QuestCompassTargetChangeEvent event)
    {
        if(event.getLocation().getWorld().getPlayers().contains(event.getPlayer())) {
            PacketHandler.sendPacketTargetLocation(event.getPlayer(), event.getLocation());
        }
    }
}
