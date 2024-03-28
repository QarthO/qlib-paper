package gg.quartzdev.lib.qpaperpluginlib.messages;

import gg.quartzdev.lib.qpaperpluginlib.storage.QConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class QMessagesManager {

    private final HashMap<String, QMessage> messageMap;
    private final QMessage FALLBACK_MESSAGE;
    private final QConfiguration messagesConfig;

    public QMessagesManager(QConfiguration msgFileConfig, String fallbackMessage){
        messageMap = new HashMap<>();
        FALLBACK_MESSAGE = new QMessage(fallbackMessage);
        this.messagesConfig = msgFileConfig;
        loadMessages();
    }

    /**
     * Loads all the raw messages from message.yml and adds them to the messages map
     */
    public void loadMessages(){
        Map<String, Object> rawMessages = messagesConfig.getValues(false);
        rawMessages.forEach((path, text) -> setMessage(path, text.toString()));
    }

    /**
     *
     * @param path
     * @param text
     */
    public void setMessage(String path, String text){
        messageMap.put(path, new QMessage(text));
    }

    /**
     * Get a {@link QMessage} from the given path/name found in the messages.yml file
     * @param path the name of the {@link QMessage} to get
     * @return A {@link QMessage}
     */
    public @NotNull QMessage get(String path){
        return messageMap.getOrDefault(path, FALLBACK_MESSAGE);
    }

}
