package gg.quartzdev.lib.qlibpaper.commands;

import gg.quartzdev.lib.qlibpaper.QLogger;
import gg.quartzdev.lib.qlibpaper.lang.GenericMessages;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class QCommandMap
{

    private final HashMap<String, CommandManager> commands;

    public QCommandMap()
    {
        commands = new HashMap<>();
    }

    /**
     * creates a new command
     *
     * @param label   command label
     * @param command command that is run if no arguments
     * @param aliases list of aliases for the command label
     */
    public void create(String label, QCommand command, List<String> aliases)
    {
        if (commands.containsKey(label))
        {
            QLogger.error(GenericMessages.EXCEPTION_CMD_EXISTS.parse("command", label).get());
            return;
        }
        CommandManager commandManager = new CommandManager(label.toLowerCase(), aliases);
        commandManager.addSubCommand("", command);

        commands.put(label, commandManager);
    }

    public void addSubCommand(String label, QCommand command)
    {
        CommandManager cmd = commands.get(label);
        if (cmd == null)
        {
            QLogger.error(GenericMessages.ERROR_INTERNAL_API_EXCEPTION.get());
            return;
        }
        cmd.addSubCommand(command.commandName, command);
    }

    public void unregister(CommandManager command)
    {
        command.unregister(Bukkit.getCommandMap());
    }

    public void unregisterAll()
    {
        commands.values().forEach((this::unregister));
    }
}

