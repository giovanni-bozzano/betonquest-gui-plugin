package com.giovannibozzano.betonquestgui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.betonquest.betonquest.api.QuestCompassTargetChangeEvent;


public class EventListener implements Listener {

    @EventHandler
    public void QuestCompassTargetChangeEventListener(QuestCompassTargetChangeEvent event)
    {
        if(event.getLocation().getWorld() == event.getProfile().getOnlineProfile().getOnlinePlayer().getWorld()) {
            PacketHandler.sendPacketTargetLocation(event.getProfile().getOnlineProfile().getOnlinePlayer(), event.getLocation());
        }
    }
}
