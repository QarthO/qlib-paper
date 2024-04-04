package gg.quartzdev.lib.qlibpaper;
public interface QPluginAPI {
    String getVersion();
    String getName();
    void setupMetrics();
    void setupConfig();
    void registerListeners();
    void registerCommands();
}
