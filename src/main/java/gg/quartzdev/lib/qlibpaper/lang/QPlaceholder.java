package gg.quartzdev.lib.qlibpaper.lang;

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
