package gg.quartzdev.lib.qlibpaper.util;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class QPaperPlugin<T extends JavaPlugin> {

    T javaPlugin;


    public QPaperPlugin(T plugin){
        this.javaPlugin = plugin;
    }

    public T getPlugin(){
        return javaPlugin;
    }

    abstract void registerCommands();
    abstract void registerEvents();


}
