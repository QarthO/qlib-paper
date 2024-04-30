package gg.quartzdev.lib.qlibpaper.storage;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

public class ConfigOption<T> extends BukkitRunnable implements IConfigOption {
    final String STORAGE_PATH;
    QConfiguration storageConfiguration;
    T value;
    BukkitRunnable loader;

    public ConfigOption(QConfiguration storageConfiguration, String storagePath, T defaultValue){
        this.storageConfiguration = storageConfiguration;
        this.STORAGE_PATH = storagePath;
        this.value = defaultValue;
    }

    public void loadValue(){
        value = (T) storageConfiguration.get(STORAGE_PATH);
    }

    public void test(Consumer<T> test){
        test.accept(value);
    }

    public void t(){
        test((var) -> this.storageConfiguration.yamlConfiguration.get(var.toString()));
    }

    public void setValue(T value){
        this.value = value;
        storageConfiguration.yamlConfiguration.set(STORAGE_PATH, value);
        storageConfiguration.save();
    }

    @Override
    public void run() {

    }
}
