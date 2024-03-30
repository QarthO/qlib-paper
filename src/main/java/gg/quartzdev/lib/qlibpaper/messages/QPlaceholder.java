package gg.quartzdev.lib.qlibpaper.messages;

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;

public enum QPlaceholder {

    FILE,
    WORLD,
    PLAYER_NAME;

    public String get(){
        return "<" + this.name().toLowerCase() + ">";
    }

    @Override
    public String toString(){
        return get();
    }

    public String file(File file){
        return file.isDirectory() ? file.getName() + " Directory" : file.getName();
    }

    public String world(World world){
        return world.getName();
    }

    public String player(Player player){
        return player.getName();
    }

}
