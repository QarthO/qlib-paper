package gg.quartzdev.lib.qlibpaper.messages;

public class GenericMessages {

    public static final QMessage CONSOLE_PREFIX = new QMessage( "<gray>[<red>q<aqua>Plugin<gray>]");
    public static final QMessage CHAT_PREFIX = new QMessage( "<red>q<aqua>Plugin <bold><gray>></bold>");

    public static final QMessage PLUGIN_INFO = new QMessage( "<prefix> <green>Running version <gray><version>");
    public static final QMessage RELOAD_COMPLETE = new QMessage( "<prefix> <green>Config reloaded");
    public static final QMessage PLUGIN_DISABLE = new QMessage( "<prefix> Disabling...");
    public static final QMessage PLUGIN_UNSAFE_DISABLE = new QMessage( "<prefix> <yellow>Caution: Reloading the server might cause issues. Try restarting the server next time");
    public static final QMessage ERROR_CMD_NOT_FOUND = new QMessage( "<prefix> <red>Error: Command not found: <yellow><cmd>");
    public static final QMessage ERROR_NO_PERMISSION = new QMessage( "<prefix> <red>Error: You don't have permission to perform this");
    public static final QMessage ERROR_PLAYER_ONLY_COMMAND = new QMessage( "<prefix> <red>Error: You must be a player to run this command");
    public static final QMessage ERROR_CONSOLE_ONLY_COMMAND = new QMessage( "<prefix> <red>Error: This command can only be ran from the console");
    public static final QMessage ERROR_PLUGIN_ENABLE = new QMessage("<prefix> <red>Error: Plugin already enabled. Most likely caused by unsupported plugin managers or an addon");
    public static final QMessage FILE_CREATED = new QMessage("<prefix> <green>Created file: <yellow><file>");
    public static final QMessage ERROR_CREATE_FILE = new QMessage("<prefix> <green>Created file: <yellow><file>");
    public static final QMessage ERROR_SAVE_FILE = new QMessage("<prefix> <green>Created file: <yellow><file>");
    public static final QMessage ERROR_CORRUPT_FILE = new QMessage("<prefix> <green>Created file: <yellow><file>");

}
