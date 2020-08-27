package com.giovannibozzano.betonquestgui;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import pl.betoncraft.betonquest.conversation.Conversation;
import pl.betoncraft.betonquest.conversation.ConversationIO;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class PacketHandler implements PluginMessageListener
{
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message)
    {
        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        switch (input.readByte()) {
            case 2:
                if (Conversation.getConversation(PlayerConverter.getID(player)) != null) {
                    ConversationIO inOut = Conversation.getConversation(PlayerConverter.getID(player)).getIO();
                    if (inOut instanceof GuiConversationIO) {
                        ((GuiConversationIO) inOut).checkClose();
                    }
                }
                break;
            case 6:
                if (Conversation.getConversation(PlayerConverter.getID(player)) != null) {
                    Conversation.getConversation(PlayerConverter.getID(player)).passPlayerAnswer(input.readInt());
                }
        }
    }

    public static void sendPacketCreateGui(Player player)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(0);
        player.getPlayer().sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }

    public static void sendPacketOpenGui(Player player)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(1);
        player.getPlayer().sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }

    public static void sendPacketCloseGui(Player player)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(2);
        player.getPlayer().sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }

    public static void sendPacketAllowCloseGui(Player player)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(3);
        player.getPlayer().sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }

    public static void sendPacketNpcDialog(Player player, String npcName, String text)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(4);
        output.writeUTF(npcName);
        output.writeUTF(text);
        player.getPlayer().sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }

    public static void sendPacketPlayerChoice(Player player, int id, String text)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(5);
        output.writeInt(id);
        output.writeUTF(text);
        player.getPlayer().sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }
}
