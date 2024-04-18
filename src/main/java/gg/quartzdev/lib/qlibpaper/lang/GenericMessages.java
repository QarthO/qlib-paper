package gg.quartzdev.lib.qlibpaper.lang;

public class GenericMessages {

    public static final QMessage CONSOLE_PREFIX = new QMessage( "<gray>[<red>q<aqua>Plugin<gray>]");
    public static final QMessage CHAT_PREFIX = new QMessage( "<red>q<aqua>Plugin <bold><gray>></bold>");

    public GenericMessages(String consolePrefix, String chatPrefix){
        CONSOLE_PREFIX.set(consolePrefix);
        CHAT_PREFIX.set(chatPrefix);
    }

    public static final QMessage PLUGIN_INFO = new QMessage( "<prefix> <green>Running version <gray><version>");
    public static final QMessage PLUGIN_ENABLE = new QMessage( "<prefix> Enabling...");
    public static final QMessage PLUGIN_DISABLE = new QMessage( "<prefix> Disabling...");
    public static final QMessage PLUGIN_UNSAFE_DISABLE = new QMessage( "<prefix> <yellow>Caution: Reloading the server might cause issues. Try restarting the server next time");

    public static final QMessage ERROR_CMD_NOT_FOUND = new QMessage( "<prefix> <red>Error: Command not found: <yellow><cmd>");
    public static final QMessage ERROR_NO_PERMISSION = new QMessage( "<prefix> <red>Error: You don't have permission to perform this");
    public static final QMessage ERROR_PLAYER_ONLY_COMMAND = new QMessage( "<prefix> <red>Error: You must be a player to run this command");
    public static final QMessage ERROR_CONSOLE_ONLY_COMMAND = new QMessage( "<prefix> <red>Error: This command can only be ran from the console");
    public static final QMessage ERROR_PLUGIN_ENABLE = new QMessage("<prefix> <red>Error: Plugin already enabled. Most likely caused by unsupported plugin managers or an addon");

//   File
    public static final QMessage FILE_CREATE = new QMessage("<prefix> <green>Created file: <yellow><file>");
    public static final QMessage FILE_RELOAD = new QMessage( "<prefix> <green>Reloaded <yellow>'<file>'");
    public static final QMessage ERROR_FILE_CREATE = new QMessage("<prefix> <green>Created file: <yellow><file>");
    public static final QMessage ERROR_FILE_SAVE = new QMessage("<prefix> <green>Created file: <yellow><file>");
    public static final QMessage ERROR_CORRUPT_FILE = new QMessage("<prefix> <red>Error: Unable to read file<yellow><file>");
    public static final QMessage LOG_BROADCAST_SOUND = new QMessage("<prefix> <blue>Broadcasting a sound to all players: <sound>");
    public static final QMessage ERROR_COMMAND_NOT_FOUND = new QMessage("<prefix> <red>Error: Command not found: <yellow><command>");
}
