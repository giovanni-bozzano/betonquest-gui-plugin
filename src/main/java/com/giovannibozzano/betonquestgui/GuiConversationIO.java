package com.giovannibozzano.betonquestgui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import pl.betoncraft.betonquest.conversation.Conversation;
import pl.betoncraft.betonquest.conversation.ConversationIO;
import pl.betoncraft.betonquest.utils.PlayerConverter;

import java.util.HashMap;
import java.util.Map;

public class GuiConversationIO implements Listener, ConversationIO
{
    private final Map<Integer, String> options = new HashMap<>();
    private final Conversation conversation;
    private final Player player;
    private String response = null;
    private int optionsIndex = 0;
    private String npcName;
    private boolean allowClose = false;

    public GuiConversationIO(Conversation conversation, String playerId)
    {
        this.conversation = conversation;
        this.player = PlayerConverter.getPlayer(playerId);
        PacketHandler.sendPacketCreateGui(this.player);
        Bukkit.getPluginManager().registerEvents(this, BetonQuestGui.INSTANCE.getBetonQuest());
    }

    @Override
    public void setNpcResponse(String npcName, String response)
    {
        this.npcName = npcName;
        this.response = response.replace("%quester%", npcName).replace("%player%", this.player.getName()).replace('&', '§');
    }

    @Override
    public void addPlayerOption(String option)
    {
        this.optionsIndex += 1;
        this.options.put(this.optionsIndex, option.replace("%quester%", this.npcName).replace("%player%", this.player.getName()).replace('&', '§'));
    }

    @Override
    public void display()
    {
        if (this.response == null) {
            this.end();
            return;
        }
        if (this.options.isEmpty()) {
            this.end();
        }
        PacketHandler.sendPacketNpcDialog(this.player, this.npcName, this.response);
        for (int i = 0; i < this.options.size(); i++) {
            PacketHandler.sendPacketPlayerChoice(this.player, i, this.options.get(i + 1));
        }
    }

    @Override
    public void clear()
    {
        this.response = null;
        this.options.clear();
        this.optionsIndex = 0;
    }

    @Override
    public void end()
    {
        this.allowClose = true;
        if (this.response == null) {
            PacketHandler.sendPacketCloseGui(this.player);
        } else if (this.options.isEmpty()) {
            PacketHandler.sendPacketAllowCloseGui(this.player);
        }
    }

    public void checkClose()
    {
        if (this.allowClose) {
            HandlerList.unregisterAll(this);
            return;
        }
        if (this.conversation.isMovementBlock()) {
            new BukkitRunnable()
            {
                public void run()
                {
                    GuiConversationIO.this.player.teleport(GuiConversationIO.this.conversation.getLocation());
                    PacketHandler.sendPacketOpenGui(GuiConversationIO.this.player);
                }
            }.runTask(BetonQuestGui.INSTANCE.getBetonQuest());
        } else {
            this.conversation.endConversation();
            HandlerList.unregisterAll(this);
        }
    }
}
