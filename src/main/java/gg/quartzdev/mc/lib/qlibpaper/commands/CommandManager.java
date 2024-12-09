package gg.quartzdev.mc.lib.qlibpaper.commands;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;

public class CommandManager
{

    private final HashMap<String, QCMD> commands = new HashMap<>();
    private final JavaPlugin plugin;


    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Adds a command along with its aliases. After adding all commands call {@link #registerCommands()} to register them.
     * @param command the command to add
     * @param aliases the aliases for the command
     */
    public void add(QCMD command, @Nullable Collection<String> aliases) {
        if(aliases != null) command.aliases(aliases);
        commands.put(command.label(), command);
    }

    @SuppressWarnings("UnstableApiUsage")
    public void registerCommands(){
        plugin.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,
                event ->
                {
                    for(QCMD command : commands.values()){
                        event.registrar().register(command.label(), command.description(), command.aliases(), command);
                    }
                }
        );
    }

    public void unregister(QCMD command) {
        commands.remove(command.label());
    }
}