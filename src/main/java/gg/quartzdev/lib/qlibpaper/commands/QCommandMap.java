package gg.quartzdev.lib.qlibpaper.commands;

import gg.quartzdev.lib.qlibpaper.messages.GenericMessages;
import gg.quartzdev.lib.qlibpaper.util.QLogger;

import java.util.HashMap;

public class QCommandMap {

    private HashMap<String, SubCommandManager> commands;

    public QCommandMap(){
        commands = new HashMap<>();
        setupDefaultCommand();
    }

    public void setupDefaultCommand(){
//        SubCommandManager baseCmd = new SubCommandManager(
//                QPlugin.getName(),
//                List.of(QPlugin.getName().replaceFirst("q", "")));
//        commands.put(QPlugin.getName().toLowerCase(), baseCmd);
    }

    public void add(String label, QCommand command){
        SubCommandManager cm = commands.get(label);
        if(cm == null){
            QLogger.error("api error: command not found");
            return;
        }
        cm.add(label, command);
    }

}

