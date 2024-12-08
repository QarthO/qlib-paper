package gg.quartzdev.mc.lib.qlibpaper;

public interface QPluginAPI
{
    void setupConfig();

    void registerListeners();

    void registerCommands();

    void setupMetrics(int pluginId);
}
