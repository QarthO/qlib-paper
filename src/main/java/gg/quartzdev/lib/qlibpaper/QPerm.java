package gg.quartzdev.lib.qlibpaper;

public enum QPerm {

    COMMAND,
    GROUP_PLAYER,
    GROUP_MOD,
    GROUP_ADMIN;

    private String node;
    private String prefix;

    QPerm(){
        node = name();
    }

    @Override
    public String toString(){
        return get();
    }

    public QPerm cmd(String commandName){
        node += commandName.replaceAll(" ", "-");
        return this;
    }

    public QPerm prefix(String prefix){
        this.prefix = prefix;
        return this;
    }

    public String get() {
        String result = node;
        node = name();
        return (prefix.toLowerCase() + "." + this.get().replaceAll("_", ".")).toLowerCase();
    }

}
