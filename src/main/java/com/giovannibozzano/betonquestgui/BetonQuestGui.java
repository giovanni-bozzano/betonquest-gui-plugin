package com.giovannibozzano.betonquestgui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.betonquest.betonquest.BetonQuest;

public final class BetonQuestGui extends JavaPlugin
{
    public static BetonQuestGui INSTANCE;
    private BetonQuest betonQuest;

    @Override
    public void onEnable()
    {
        INSTANCE = this;
        if (this.getServer().getPluginManager().isPluginEnabled("BetonQuest"))
        {
            this.betonQuest = (BetonQuest) this.getServer().getPluginManager().getPlugin("BetonQuest");
            this.betonQuest.registerConversationIO("gui", GuiConversationIO.class);
            Bukkit.getMessenger().registerOutgoingPluginChannel(this, "betonquestgui:main");
            Bukkit.getMessenger().registerIncomingPluginChannel(this, "betonquestgui:main", new PacketHandler());
            Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        }
    }

    public BetonQuest getBetonQuest()
    {
        return this.betonQuest;
    }
}
