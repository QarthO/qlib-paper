package gg.quartzdev.lib.qlibpaper.commands;

import gg.quartzdev.lib.qlibpaper.messages.GenericMessages;
import gg.quartzdev.lib.qlibpaper.util.QPerm;
import gg.quartzdev.lib.qlibpaper.util.Sender;
import org.bukkit.command.CommandSender;

public abstract class QCommand {

    String name;
    QPerm permissionGroup;

    public QCommand(String name, QPerm permissionGroup){
        this.name = name;
        this.permissionGroup = permissionGroup;

    }

    private boolean hasPermission(CommandSender sender){
        return sender.hasPermission(permissionGroup.get()) || sender.hasPermission(QPerm.COMMAND.cmd(name));
    }

    public boolean run(CommandSender sender, String label, String[] args){
        //        checks permission
        if(!this.hasPermission(sender)){
            Sender.message(sender, GenericMessages.ERROR_NO_PERMISSION);
            return false;
        }
//        runs command
        return logic(sender, label, args);
    }

    public abstract boolean logic(CommandSender sender, String label, String[] args);

    public Iterable<String> getTabCompletions(CommandSender sender, String[] args){
        if(!this.hasPermission(sender)){
            return null;
        }
        return this.tabCompletionLogic(sender, args);
    }
    public abstract Iterable<String> tabCompletionLogic(CommandSender sender, String[] args);

}
