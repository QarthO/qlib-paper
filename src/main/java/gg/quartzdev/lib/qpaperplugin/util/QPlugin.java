package gg.quartzdev.lib.qpaperplugin.util;

import gg.quartzdev.lib.qpaperplugin.storage.Config;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class QPlugin {

    public static QPlugin instance;
    private static JavaPlugin javaPlugin;
    private static Config config;
    private boolean selfDisabled = false;
    private QCommandMap commandMap;

    public static JavaPlugin getPlugin(){
        return javaPlugin;
    }

    public static Config getConfig(){
        return config;
    }

    private QPlugin(JavaPlugin plugin, boolean useConfig, int bStatsPluginId){
        javaPlugin = plugin;

        if(useConfig){
            setupPluginConfig();
        }

        if(bStatsPluginId > 0){
            setupMetrics(bStatsPluginId);
        }

        registerCommands();
        registerListeners();
    }

    public static void enable(JavaPlugin plugin, boolean useConfig, int bStatsPluginId){
        if(instance != null){
            QLogger.error(Messages.ERROR_PLUGIN_ENABLE);
            return;
        }
        instance = new QPlugin(plugin, useConfig, bStatsPluginId);
    }

    public static void disable(boolean selfDisabled){

//
        instance.selfDisabled = selfDisabled;

//        Warns about reloading
        final boolean isStopping = Bukkit.getServer().isStopping();
        if(!isStopping && !instance.selfDisabled){
            QLogger.warning(Messages.PLUGIN_UNSAFE_DISABLE);
        }

        QLogger.info(Messages.PLUGIN_DISABLE);
        instance = null;
        javaPlugin = null;
        config = null;

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
            QLogger.error(Messages.ERROR_CREATE_FILE.parse("file", "Plugin Data Folder"));
        }
    }

    public void setupPluginConfig(){
        createDataFolder();
        config = new Config("config.yml");
    }

    public void registerCommands(){
        new QCommandMap();
    }

    public void registerListeners(){

    }
}
