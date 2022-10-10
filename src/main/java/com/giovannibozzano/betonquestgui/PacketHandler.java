package com.giovannibozzano.betonquestgui;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.betonquest.betonquest.utils.PlayerConverter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.betonquest.betonquest.conversation.Conversation;
import org.betonquest.betonquest.conversation.ConversationIO;

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
        player.sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }

    public static void sendPacketOpenGui(Player player)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(1);
        player.sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }

    public static void sendPacketCloseGui(Player player)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(2);
        player.sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }

    public static void sendPacketAllowCloseGui(Player player)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(3);
        player.sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }

    public static void sendPacketNpcDialog(Player player, String npcName, String text)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(4);
        output.writeUTF(npcName);
        output.writeUTF(text);
        player.sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }

    public static void sendPacketPlayerChoice(Player player, int id, String text)
    {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(5);
        output.writeInt(id);
        output.writeUTF(text);
        player.sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }

    public static void sendPacketTargetLocation(Player player, Location  location){
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeByte(7);
        output.writeInt(location.getBlockX());
        output.writeInt(location.getBlockY());
        output.writeInt(location.getBlockZ());
        player.sendPluginMessage(BetonQuestGui.INSTANCE, "betonquestgui:main", output.toByteArray());
    }
}
