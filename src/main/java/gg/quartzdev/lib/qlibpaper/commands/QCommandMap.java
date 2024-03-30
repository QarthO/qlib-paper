package gg.quartzdev.lib.qlibpaper.commands;

import gg.quartzdev.lib.qlibpaper.util.QLogger;
import gg.quartzdev.lib.qlibpaper.util.QPlugin;

import java.util.HashMap;
import java.util.List;

public class QCommandMap {

    private HashMap<String, SubCommandManager> commands;

    public QCommandMap(){
        commands = new HashMap<>();
        setupDefaultCommand();
    }

    public void setupDefaultCommand(){
        SubCommandManager baseCmd = new SubCommandManager(
                QPlugin.getName(),
                List.of(QPlugin.getName().replaceFirst("q", "")));
        commands.put(QPlugin.getName().toLowerCase(), baseCmd);
    }

    public void add(String label, QCommand command){
        SubCommandManager cm = commands.get(label);
        if(cm == null){
            QLogger.error(QPlugin.genericMessages.get("ERROR_SAVE_FILE"));
            return;
        }
        cm.add(label, command);
    }

}

