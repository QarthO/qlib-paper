package gg.quartzdev.lib.qlibpaper.messages;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;

public enum QPlaceholder {

    FILE,
    WORLD,
    PLAYER_NAME,
    COMMAND,
    SOUND;

    public String get(){
        return "<" + this.name().toLowerCase() + ">";
    }
    @Override
    public String toString(){
        return get();
    }

}
