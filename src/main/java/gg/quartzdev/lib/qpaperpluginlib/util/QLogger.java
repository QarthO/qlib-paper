package gg.quartzdev.lib.qpaperpluginlib.util;

import gg.quartzdev.lib.qpaperpluginlib.messages.QMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Tameable;

import java.util.logging.Level;

public class QLogger {
    /**
     * Logs a general message
     * @param msg - String to log
     */
    public static void info(String msg){
        Sender.message(Bukkit.getConsoleSender(), msg);
    }
    /**
     * Logs a general message
     * @param msg - Message to log
     */
    public static void info(QMessage msg){
        Sender.message(Bukkit.getConsoleSender(), msg);
    }

    /**
     * Logs a warning
     * @param msg
     */
    public static void warning(String msg){
        info("<yellow>" + msg);
//        TODO: Optional log warnings to file
    }

    /**
     * Logs a warning
     * @param msg
     */
    public static void warning(QMessage msg){
        info("<yellow>" + msg.get());
//        TODO: Optional log warnings to file
    }

    /**
     * Logs error
     * @param msg
     */
    public static void error(String msg){
        info("<red>" + msg);
//        TODO: Optional log errors to file
    }

    public static void error(QMessage msg){
        info("<red>" + msg.get());
//        TODO: Optional log errors to file
    }

    public static void error(StackTraceElement[] error){
        for(StackTraceElement e : error){
            error(e.toString());
        }
    }
}
