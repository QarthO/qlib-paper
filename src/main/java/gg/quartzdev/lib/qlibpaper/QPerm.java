package gg.quartzdev.lib.qlibpaper;

public class QPerm {

    public static String COMMAND = "command.";
    public static String GROUP_PLAYER = "group.player";
    public static String GROUP_MOD = "group.mod";
    public static String GROUP_ADMIN = "group.admin";

    public QPerm(String permissionPrefix){
        COMMAND = permissionPrefix + COMMAND;
        GROUP_PLAYER = permissionPrefix + GROUP_PLAYER;
        GROUP_MOD = permissionPrefix + GROUP_MOD;
        GROUP_ADMIN = permissionPrefix + GROUP_ADMIN;
    }

}
