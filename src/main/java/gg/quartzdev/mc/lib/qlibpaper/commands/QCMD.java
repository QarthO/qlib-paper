package gg.quartzdev.mc.lib.qlibpaper.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public abstract class QCMD implements BasicCommand
{
    /*
        the command label
        ie: /<label>
     */
    private final String label;

    /*
        bukkit command description
     */
    private final String description;

    /*
        whether a player needs to have the permission to use the command
        a player with the permission set to false will still not be able to use the command regardless of this value
     */
    private boolean requiresPermission = true;

    /*
        permission node to use the command
     */
    private final String permission;

    /*
        alternative permission node to use the command thats typically shared by many commands
     */
    private final String permissionPackage;

    /*
        alias labels for the command
     */
    private Collection<String> aliases;

    public QCMD(@NotNull String label, @Nullable String description, @NotNull String permission, @Nullable String permissionPackage, boolean requiresPermission){
        this.label = label;
        this.description = description;
        this.permission = permission;
        this.permissionPackage = permissionPackage == null ? permission : permissionPackage;
        this.requiresPermission = requiresPermission;
        this.aliases = new ArrayList<>();
    }

    @Override
    public abstract void execute(@NotNull CommandSourceStack commandSourceStack, @NotNull String[] args);

    @Override
    public abstract @NotNull Collection<String> suggest(@NotNull CommandSourceStack commandSourceStack, @NotNull String[] args);

    @Override
    public boolean canUse(@NotNull CommandSender sender)
    {
//        if we don't require permission, we still want to prevent the sender if their permission is set to false
        if(!requiresPermission)
            return !sender.isPermissionSet(permission()) || sender.hasPermission(permission());

//        if the player has the permission or the permission package, they can use the command
        return  sender.hasPermission(permission()) || sender.hasPermission(permissionPackage());
    }

    @Override
    public @NotNull String permission()
    {
        return permission;
    }

    public @NotNull String permissionPackage()
    {
        return permissionPackage;
    }

    public @NotNull String label()
    {
        return label;
    }

    public @Nullable String description()
    {
        return description;
    }

    public @NotNull Collection<String> aliases(){
        return aliases;
    }

    public void aliases(@NotNull Collection<String> aliases){
        this.aliases = aliases;
    }

    public void requiresPermission(boolean requiresPermission)
    {
        this.requiresPermission = requiresPermission;
    }
}