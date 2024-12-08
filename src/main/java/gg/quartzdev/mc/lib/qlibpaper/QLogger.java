package gg.quartzdev.mc.lib.qlibpaper;

import gg.quartzdev.mc.lib.qlibpaper.lang.QMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

import java.util.Arrays;

public class QLogger
{

    private static QLogger INSTANCE;
    private static ComponentLogger LOGGER;
    private boolean logsErrorsToFile;

    private QLogger()
    {

    }

    private QLogger(ComponentLogger logger)
    {
        LOGGER = logger;
    }

    public static void init(ComponentLogger logger)
    {
        if (INSTANCE != null)
        {
            error("Error: QLogger has already been initialized");
            return;
        }
        INSTANCE = new QLogger(logger);
    }

    private static Component parseOutPrefix(String msg)
    {
        msg = msg.strip().startsWith("<prefix>") ? msg.replaceFirst("<prefix>", "").stripLeading() : msg;
        return MiniMessage.miniMessage().deserialize(msg);
    }

    /**
     * Logs a general message
     *
     * @param msg - String to log
     */
    public static void info(String msg)
    {
        Sender.message(Bukkit.getConsoleSender(), msg);
    }

    /**
     * Logs a general message
     *
     * @param msg - Message to log
     */
    public static void info(QMessage msg)
    {
        info(msg.get());
    }

    /**
     * Logs a warning
     *
     * @param msg
     */
    public static void warning(String msg)
    {
        LOGGER.warn(parseOutPrefix(msg));
//        TODO: Optional log warnings to file
    }

    /**
     * Logs a warning
     *
     * @param msg
     */
    public static void warning(QMessage msg)
    {
        warning(msg.get());
//        TODO: Optional log warnings to file
    }

    /**
     * Logs error
     *
     * @param msg
     */
    public static void error(String msg)
    {
        LOGGER.error(parseOutPrefix(msg));
//        TODO: Optional log errors to file
    }

    public static void error(QMessage msg)
    {
        error(msg.get());
//        TODO: Optional log errors to file
    }

    public static void error(StackTraceElement[] error)
    {
        error(Arrays.toString(error));
    }
}
