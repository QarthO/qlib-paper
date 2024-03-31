package gg.quartzdev.lib.qlibpaper;

import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.lang.QMessage;
import gg.quartzdev.lib.qlibpaper.lang.QPlaceholder;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *  Easy way to send information to a player or console. All text messages supports MiniMessage format
 */
public class Sender {

    private static String CONSOLE_PREFIX = "<gray>[<red>q<aqua>Plugin<gray>]";
    private static String CHAT_PREFIX = "<gray>[<red>q<aqua>Plugin<gray>]";

    public Sender(String consolePrefix, String chatPrefix){
        CONSOLE_PREFIX = consolePrefix;
        CHAT_PREFIX = chatPrefix;
    }

    /**
     * Sends a message to the player in chat
     * @param sender the {@link CommandSender} to send the message to
     * @param message the message as a {@link String} to send supporting MiniMessage formatting
     */
    public static void message(CommandSender sender, String message){
        if(sender instanceof Player)
            sender.sendMessage(parse(message, false));
        else
            sender.sendMessage(parse(message, true));
    }

    /**
     * Sends a Message to the player in chat
     * @param sender the {@link CommandSender} to send the message to
     * @param message the {@link QMessage} to send supporting MiniMessage formatting
     */
    public static void message(CommandSender sender, QMessage message){
        message(sender, message.get());
    }

    /**
     * Sends a message to the player in chat
     * @param player the {@link Player} to send the message to
     * @param message the message to send
     */
    public static void message(Player player, QMessage message){
        message(player, message.get());
    }

    /**
     * Sends a message to the player in chat
     * @param player the {@link Player} to send the message to
     * @param message the message to send
     */
    public static void message(Player player, String message){
        player.sendMessage(parse(message, false));

    }

    /**
     * Sends an action bar to a player
     * @param player the player to send the actionbar to
     * @param message the message to send supporting MiniMessage formatting
     */
    public static void actionBar(Player player, String message){
        player.sendActionBar(parse(message, false));
    }

    /**
     * Sends an action bar to a player
     * @param player the player to send the actionbar to
     * @param message the {@link QMessage} to send supporting MiniMessage formatting
     */
    public static void actionBar(Player player, QMessage message){
        player.sendActionBar(parse(message.get(), false));
    }

    /**
     * Broadcast a message to all online players including the console
     * @param message the message to send supporting MiniMessage formatting
     */
    public static void broadcast(String message){
//        Sends a message to all online players
        Audience.audience(Bukkit.getOnlinePlayers()).sendMessage(parse(message, false));
//        Sends a message to the consoles
        message(Bukkit.getConsoleSender(), message);
    }

    /**
     * Broadcast a sound to all online players, and logs it to the console
     * @param sound the {@link Sound} to broadcast. The examinableName is what's logged to the console
     */
    public static void broadcast(Sound sound){
        Audience.audience(Bukkit.getOnlinePlayers()).playSound(sound);
        GenericMessages.LOG_BROADCAST_SOUND.parse(QPlaceholder.SOUND, sound.examinableName());
    }

    /**
     * Deserializes the message into a {@link Component} and parses any MiniMessage placeholders along the plugin <prefix>
     * @param message the message to parse
     * @param isConsole whether the <prefix> is replaced with the console or chat prefix
     * @return A MiniMessage deserialized chat {@link Component}
     */
    private static Component parse(String message, boolean isConsole){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize(message,
                Placeholder.parsed("prefix", isConsole ? CONSOLE_PREFIX : CHAT_PREFIX));
    }
}
