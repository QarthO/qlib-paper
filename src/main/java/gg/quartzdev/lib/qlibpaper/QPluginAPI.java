package gg.quartzdev.lib.qlibpaper;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class QPluginAPI {
    private Metrics metrics;
    private JavaPlugin pluginInstance;

    public void setupMetrics(int pluginId){
        metrics = new Metrics(pluginInstance, pluginId);
    }

    @SuppressWarnings("UnstableApiUsage")
    public String getVersion(){
        return pluginInstance.getPluginMeta().getVersion();
    }

    public String getName(){
        return pluginInstance.getName();
    }

    abstract void setupConfig();
    abstract void registerListeners();
    abstract void registerCommands();
}
