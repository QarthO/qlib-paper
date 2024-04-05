package gg.quartzdev.lib.qlibpaper.commands;

import gg.quartzdev.lib.qlibpaper.QLogger;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.List;

public class QCommandMap {

    private HashMap<String, CommandManager> commands;

    public QCommandMap(){
        commands = new HashMap<>();
    }

    /**
     * creates a new command
     * @param label command label
     * @param command command that is ran if no arguments
     * @param aliases list of aliases for the command label
     */
    public void create(String label, QCommand command, List<String> aliases){
        if(commands.containsKey(label)){
//            TODO: have it throw an exception instead of returning
            QLogger.error("error: cmd alreayd exists");
            return;
        }
        CommandManager commandManager = new CommandManager(label.toLowerCase(), aliases);
        commandManager.add("", command);

        commands.put(label, commandManager);
    }

    public void addSubCommand(String label, QCommand command){
        CommandManager cm = commands.get(label);
        if(cm == null){
            QLogger.error("api error: command not found");
            return;
        }
        cm.add(command.commandName, command);
    }

    public void unregister(CommandManager command){
        command.unregister(Bukkit.getCommandMap());
    }

    public void unregisterAll(){
        commands.values().forEach((this::unregister));
    }

}

