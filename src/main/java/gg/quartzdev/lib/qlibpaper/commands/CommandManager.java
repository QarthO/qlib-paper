package gg.quartzdev.lib.qlibpaper.commands;

import gg.quartzdev.lib.qlibpaper.Sender;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import gg.quartzdev.lib.qlibpaper.lang.QPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandManager extends Command
{
    HashMap<String, QCommand> subCommands;

    public CommandManager(String name, @NotNull List<String> aliases)
    {
        super(name);
        super.setAliases(aliases);
        subCommands = new HashMap<>();
        Bukkit.getCommandMap().register(name, this);
    }

    public void addSubCommand(String label, QCommand subCommand)
    {
        subCommands.put(label, subCommand);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String labelOrAlias, @NotNull String[] args)
    {
//        Send base command
        if (args.length == 0)
        {
            return subCommands.get("").run(sender, labelOrAlias, args);
        }

//        Get subcommand from args
        QCommand cmd = subCommands.get(args[0]);

        if (cmd == null)
        {
            Sender.message(sender, GenericMessages.CMD_NOT_FOUND.parse(QPlaceholder.COMMAND, args[0]));
            return false;
        }

//        Run the command
        return cmd.run(sender, labelOrAlias, args);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String labelOrAlias, String[] args) throws IllegalArgumentException
    {
        List<String> completions = new ArrayList<>();

//        Only tab complete a sub command if the player has permission
        if (args.length == 1)
        {
            Set<String> allowedSubCommands = new HashSet<>();
            for (Map.Entry<String, QCommand> entry : subCommands.entrySet())
            {
                String commandName = entry.getKey();
                QCommand cmd = entry.getValue();
                if (cmd.hasPermission(sender))
                {
                    allowedSubCommands.add(commandName);
                }
            }
            StringUtil.copyPartialMatches(args[0], allowedSubCommands, completions);
        }

//        Let  the subcommand handle tab completion
        if (args.length > 1)
        {
            QCommand cmd = subCommands.get(args[0]);

            if (cmd == null)
            {
                return completions;
            }

            Iterable<String> rawCompletions = cmd.getTabCompletions(sender, args);
            if (rawCompletions != null)
            {
                StringUtil.copyPartialMatches(args[args.length - 1], rawCompletions, completions);
            }
        }

        Collections.sort(completions);
        return completions;
    }

}

