package gg.quartzdev.lib.qlibpaper.commands;

import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.QPerm;
import gg.quartzdev.lib.qlibpaper.Sender;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public abstract class QCommand {

    String commandName;
    String permissionGroup;

    /**
     *
     * @param commandName name of the command
     * @param permissionGroup permission group the command is in
     */
    public QCommand(String commandName, String permissionGroup){
        this.commandName = commandName;
        this.permissionGroup = permissionGroup;

    }

    /**
     *
     * @return the command name of the command
     */
    public String commandName(){
        return this.commandName;
    }

    /**
     *
     * @return the permission group this command is assigned to
     */
    public String permissionGroup(){
        return this.permissionGroup;
    }

    /**
     * checks if the sender has the commands permission group, or the commands specific permission
     * <plugin>.command.<command-name> (ie. 'qspleef.command.join')
     * @param sender the sender to check if they have permission
     * @return if they have permission
     */
    private boolean hasPermission(CommandSender sender){
        return sender.hasPermission(permissionGroup) || sender.hasPermission(QPerm.COMMAND + commandName.toLowerCase());
    }

    /**
     * Checks if the sender has permission then runs the command
     * @param sender who ran the commmand
     * @param label the command label
     * @param args the arguments of the command
     * @return true or false whether the command ran successfully
     */
    public boolean run(CommandSender sender, String label, String[] args){
//        checks permission
        if(this.hasPermission(sender)){
//            runs command logic
            return logic(sender, label, args);
        }
//        fails permission check
        Sender.message(sender, GenericMessages.ERROR_NO_PERMISSION);
        return false;
    }

    public abstract boolean logic(CommandSender sender, String label, String[] args);

    /**
     * Returns the tab completions based on what the command the sender has already types,
     * but first verifying the sender has permission to view the tab completions
     * @param sender the sender to send tab completions to
     * @param args the args the sender is typing
     * @return the list of tab completions
     */
    public Iterable<String> getTabCompletions(CommandSender sender, String[] args){
//        checks permission
        if(this.hasPermission(sender)){
//            sends tab completes
            return this.tabCompletionLogic(sender, args);
        }
//        sends nothing if no permission
        return null;
    }

    /**
     * The logic a {@link QCommand} uses to decide what tab completions are sent back to the {@link CommandSender}.
     * Note: This will only be called if the {@link CommandSender} has permission to run the command
     * @param sender the command sender who is typing a command
     * @param args the command sender's current arguments
     * @return list of tab completions
     */
    public abstract Iterable<String> tabCompletionLogic(CommandSender sender, String[] args);

}
