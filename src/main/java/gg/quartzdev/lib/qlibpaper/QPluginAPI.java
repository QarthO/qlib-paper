package gg.quartzdev.lib.qlibpaper;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class QPluginAPI {
    private JavaPlugin pluginInstance;

    public void setupMetrics(int pluginId){
        new Metrics(pluginInstance, pluginId);
    }

    @SuppressWarnings("UnstableApiUsage")
    public String getVersion(){
        return pluginInstance.getPluginMeta().getVersion();
    }

    public String getName(){
        return pluginInstance.getName();
    }

    public abstract void setupConfig();
    public abstract void registerListeners();
    public abstract void registerCommands();
}
