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
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 *  Easy way to send information to a player or console. All messages support MiniMessage format
 */
public class Sender {

    /**
     * Sends a message to an audience in chat
     * @param audience the audience to send the message to
     * @param message the message to send
     */
    public static void message(Audience audience, String message){
        if(message.isEmpty())
            return;
        audience.sendMessage(parse(message, audience instanceof ConsoleCommandSender));
    }

    /**
     * Sends a {@link QMessage} to an audience in chat. This just calls {@link #message(Audience, String)} by using {@link QMessage#get()}
     * @param audience the audience to send the message to
     * @param message the {@link QMessage} to send
     */
    public static void message(Audience audience, QMessage message){
        message(audience, message.get());
    }

    /**
     * Sends an action bar to an audience
     * @param audience the audience to send the actionbar to
     * @param message the message to send supporting MiniMessage formatting
     */
    public static void actionBar(Audience audience, String message){
        if(message.isEmpty()){
            return;
        }
        audience.sendActionBar(parse(message, false));
    }

    /**
     * Sends an action bar to an audience. This just calls {@link #actionBar(Audience, String)} by using {@link QMessage#get()}
     * @param audience the audience to send the actionbar to
     * @param message the {@link QMessage} to send supporting MiniMessage formatting
     */
    public static void actionBar(Audience audience, QMessage message){
        actionBar(audience, message.get());
    }

    /**
     * Sends a sound to an audience
     * @param audience the audience to send the sound to
     * @param sound the adventure {@link Sound} to send
     */
    public static void sound(Audience audience, Sound sound){
        audience.playSound(sound);
    }

    /**
     * Broadcast a message to all online players including the console
     * @param message the message to send supporting MiniMessage formatting
     */
    public static void broadcast(String message){
//        Sends a message to all online players
        message(Audience.audience(Bukkit.getOnlinePlayers()), message);
//        Sends a message to the consoles
        message(Bukkit.getConsoleSender(), message);
    }

    /**
     * Broadcast a message to all online players including the console. This just calls {@link #broadcast(String)} by using {@link QMessage#get()}
     * @param message the {@link QMessage} to send supporting MiniMessage formatting
     */
    public static void broadcast(QMessage message){
        broadcast(message.get());
    }

    /**
     * Broadcast a sound to all online players, and logs it to the console
     * @param sound the adventure {@link Sound} to broadcast. The {@link Sound#examinableName()} is what's logged to the console
     */
    public static void broadcast(Sound sound){
        Audience.audience(Bukkit.getOnlinePlayers()).playSound(sound);
        GenericMessages.LOG_BROADCAST_SOUND.parse(QPlaceholder.SOUND, sound.examinableName());
    }

    /**
     * Deserializes the message into an adventure {@link Component} and parses any MiniMessage placeholders including the plugin <prefix>
     * @param message the message to parse
     * @param isConsole whether the <prefix> is replaced with the console or chat prefix
     * @return A MiniMessage deserialized chat {@link Component}
     */
    private static Component parse(String message, boolean isConsole){
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize(message,
                Placeholder.parsed("prefix", isConsole ? GenericMessages.CONSOLE_PREFIX.get() : GenericMessages.CHAT_PREFIX.get()));
    }
}
