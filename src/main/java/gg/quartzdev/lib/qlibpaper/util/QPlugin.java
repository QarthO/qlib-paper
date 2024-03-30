package gg.quartzdev.lib.qlibpaper.util;

import gg.quartzdev.lib.qlibpaper.commands.QCommandMap;
import gg.quartzdev.lib.qlibpaper.messages.QMessagesManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class QPlugin {

    public static QPlugin instance;
    private static JavaPlugin javaPlugin;
    private boolean selfDisabled = false;
    private QCommandMap commandMap;

    public static QMessagesManager genericMessages;
    public static QMessagesManager pluginMessages;

    public static JavaPlugin getPlugin(){
        return javaPlugin;
    }

//    public static Config getConfig(){
//        return config;
//    }

    private QPlugin(JavaPlugin plugin, int bStatsPluginId){
        javaPlugin = plugin;

        if(bStatsPluginId > 0){
            setupMetrics(bStatsPluginId);
        }

        registerCommands();
        registerListeners();
    }

    public <T> T t(T t){
        return t;
    }

    public static void enable(JavaPlugin plugin, int bStatsPluginId){
        if(instance != null){
            QLogger.error(genericMessages.get("ERROR_PLUGIN_ENABLE"));
            return;
        }
        instance = new QPlugin(plugin, bStatsPluginId);
    }

    public static void disable(boolean selfDisabled){

//
        instance.selfDisabled = selfDisabled;

//        Warns about reloading
        final boolean isStopping = Bukkit.getServer().isStopping();
        if(!isStopping && !instance.selfDisabled){
            genericMessages.get("PLUGIN_UNSAFE_DISABLE");
        }
        genericMessages.get("PLUGIN_DISABLE");
        instance = null;
        javaPlugin = null;
//        config = null;

//        Put logic to stop any async tasks
    }

    public void setupMetrics(int pluginId){
        Metrics metrics = new Metrics(javaPlugin, pluginId);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static String getVersion(){
        return javaPlugin.getPluginMeta().getVersion();
    }

    public static String getName(){
        return javaPlugin.getName();
    }

    private void createDataFolder(){
        try{
            javaPlugin.getDataFolder().mkdirs();
        } catch(SecurityException exception){
            QLogger.error(genericMessages.get("ERROR_CREATE_FILE").parse("file", "Plugin Data Folder"));
        }
    }

    public void setupPluginConfig(){
        createDataFolder();
    }

    public void registerCommands(){
        new QCommandMap();
    }

    public void registerListeners(){

    }
}
