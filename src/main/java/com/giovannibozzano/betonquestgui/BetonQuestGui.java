package com.giovannibozzano.betonquestgui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.betoncraft.betonquest.BetonQuest;

public final class BetonQuestGui extends JavaPlugin
{
    public static BetonQuestGui INSTANCE;
    private BetonQuest betonQuest;

    @Override
    public void onEnable()
    {
        INSTANCE = this;
        if (this.getServer().getPluginManager().isPluginEnabled("BetonQuest")) {
            this.betonQuest = (BetonQuest) this.getServer().getPluginManager().getPlugin("BetonQuest");
            this.betonQuest.registerConversationIO("gui", GuiConversationIO.class);
            Bukkit.getMessenger().registerOutgoingPluginChannel(this, "betonquestgui:main");
            Bukkit.getMessenger().registerIncomingPluginChannel(this, "betonquestgui:main", new PacketHandler());
        }
    }

    public BetonQuest getBetonQuest()
    {
        return this.betonQuest;
    }
}
