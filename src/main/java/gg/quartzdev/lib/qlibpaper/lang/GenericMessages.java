package gg.quartzdev.lib.qlibpaper.lang;

public class GenericMessages {

    public static final QMessage CONSOLE_PREFIX = new QMessage( "[<red>q<aqua>Plugin<reset>]");
    public static final QMessage CHAT_PREFIX = new QMessage( "<red>q<aqua>Plugin <bold><gray>></bold><reset>");

    public GenericMessages(String consolePrefix, String chatPrefix){
        CONSOLE_PREFIX.set(consolePrefix);
        CHAT_PREFIX.set(chatPrefix);
    }

    public static final QMessage PLUGIN_INFO = new QMessage( "<prefix> <green>Running version <gray><version>");
    public static final QMessage PLUGIN_ENABLE = new QMessage( "<prefix> Enabling...");
    public static final QMessage PLUGIN_DISABLE = new QMessage( "<prefix> Disabling...");
    public static final QMessage ERROR_PLUGIN_ENABLE = new QMessage("<prefix> <red>Error: Plugin is already enabled. Most likely caused by using a plugin manager or an unsupported addon");
    public static final QMessage ERROR_NO_PERMISSION = new QMessage( "<prefix> <red>Error: You don't have permission to perform this");
    public static final QMessage ERROR_PLAYER_ONLY_COMMAND = new QMessage( "<prefix> <red>You must be a player to run this command");
    public static final QMessage ERROR_CONSOLE_ONLY_COMMAND = new QMessage( "<prefix> <red>This command can only be ran from the console");
    public static final QMessage CMD_NOT_FOUND = new QMessage("<prefix> <red>Unknown command: <yellow><command>");
    public static final QMessage ERROR_UPDATE_MESSAGE = new QMessage("<prefix> <red>Error updating message: <yellow><key>");
    public static final QMessage LOG_BROADCAST_SOUND = new QMessage("<prefix> <blue>Broadcasting sound: <sound>");

//   File
    public static final QMessage FILE_CREATE = new QMessage("<prefix> <green>Successfully created file: <yellow><file>");
    public static final QMessage ERROR_FILE_CREATE = new QMessage("<prefix> <red>Error creating file: <yellow><file>");
    public static final QMessage ERROR_FILE_NOT_FOUND = new QMessage("<prefix> <red>File not found: <yellow><file>");
    public static final QMessage FILE_LOADING = new QMessage("<prefix> Loading file: <yellow><file>");
    public static final QMessage FILE_RELOAD = new QMessage( "<prefix> <green>Successfully reloaded file: <yellow><file>");
    public static final QMessage ERROR_RELOAD = new QMessage( "<prefix> <red>Error reloading file: <yellow><file>");
    public static final QMessage FILE_SAVE = new QMessage("<prefix> <green>Successfully saved file: <yellow><file>");
    public static final QMessage ERROR_FILE_SAVE = new QMessage("<prefix> <red>Error saving file: <yellow><file>");
    public static final QMessage ERROR_CORRUPT_FILE = new QMessage("<prefix> <red>Error reading file: <yellow><file>");

//    Update Checker
    public static final QMessage UPDATE_CHECKING = new QMessage("<prefix> <reset>Checking for updates...");
    public static final QMessage UPDATE_AVAILABLE = new QMessage("<prefix> <green>Version <yellow><version><green> available!");
    public static final QMessage UPDATE_DOWNLOAD_URL = new QMessage("<prefix> <green>Download at: <light_purple><click:open_url:<download_url>><download_url></click>");
    public static final QMessage UPDATE_NOT_AVAILABLE = new QMessage("<prefix> <green>You're using the latest version");
    public static final QMessage ERROR_UPDATE_CHECK = new QMessage("<prefix> <red>Error checking for updates");
}
