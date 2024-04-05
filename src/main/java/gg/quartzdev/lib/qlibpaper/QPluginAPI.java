package gg.quartzdev.lib.qlibpaper;

public interface QPluginAPI {
    void setupConfig();
    void registerListeners();
    void registerCommands();
    void setupMetrics(int pluginId);
}
