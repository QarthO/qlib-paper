package gg.quartzdev.mc.lib.qlibpaper.lang;

@SuppressWarnings("unused")
public enum QPlaceholder
{
    FILE,       // <file> - The file name
    WORLD,      // <world> - The world name
    PLAYER,     // <player> - The player name
    COMMAND,    // <command> - The command name
    SOUND;      // <sound> - The sound name

    public String get()
    {
        return "<" + this.name().toLowerCase() + ">";
    }

    @Override
    public String toString()
    {
        return get();
    }
}
