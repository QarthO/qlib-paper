package gg.quartzdev.lib.qlibpaper.commands;

import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.lang.QPlaceholder;
import gg.quartzdev.lib.qlibpaper.Sender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CommandManager extends Command {

    HashMap<String, QCommand> subCommands;
    public CommandManager(String name, @NotNull List<String> aliases){
        super(name);
        super.setAliases(aliases);
        Bukkit.getCommandMap().register(name, this);
    }

    public void add(String label, QCommand subCommand){
        subCommands.put(label, subCommand);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String labelOrAlias, @NotNull String[] args) {
//        Send base command
        if(args.length == 0){
            return subCommands.get("").run(sender, labelOrAlias, args);
        }

//        Get subcommand from args
        QCommand cmd = subCommands.get(args[0]);

        if(cmd == null){
            Sender.message(sender, GenericMessages.ERROR_COMMAND_NOT_FOUND.parse(QPlaceholder.COMMAND, args[0]));
            return false;
        }

//        Run the command
        return cmd.run(sender, labelOrAlias, args);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String labelOrAlias, String[] args) throws IllegalArgumentException {
        List<String> completions = new ArrayList<>();
//
        if(args.length == 1){
            StringUtil.copyPartialMatches(args[0], subCommands.keySet(), completions);
        }

        if(args.length > 1){
            QCommand cmd = subCommands.get(args[0]);

            if(cmd == null) {
                return completions;
            }

            Iterable<String> rawCompletions = cmd.getTabCompletions(sender, args);
            if(rawCompletions != null) {
                StringUtil.copyPartialMatches(args[args.length-1], rawCompletions, completions);
            }
        }

        Collections.sort(completions);
        return completions;
    }

}

