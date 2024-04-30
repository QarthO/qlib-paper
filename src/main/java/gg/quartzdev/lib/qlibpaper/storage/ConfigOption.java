package gg.quartzdev.lib.qlibpaper.storage;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigOption<T> implements IConfigOption {
    private T value;
    private final Supplier<T> loader;
    private final Consumer<T> saver;

    public ConfigOption(Supplier<T> loader, Consumer<T> saver){
        this.loader = loader;
        this.saver = saver;
        load();
    }

    public void load(){
        value = loader.get();
    }

    public T get(){
        return value;
    }

    public void setValue(T value){
        this.value = value;
    }

    public void save(){
        saver.accept(value);
    }

}
