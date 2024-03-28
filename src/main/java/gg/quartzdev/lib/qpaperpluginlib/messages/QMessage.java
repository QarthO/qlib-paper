package gg.quartzdev.lib.qpaperpluginlib.messages;

public class QMessage {

    private final String message;
    private String parsed;

    public QMessage(String message){
        this.message = message;
    }

    /**
     * Gets the parsed {@link String} message and resets the parsed message back to the original message.
     * This should be called after parsing all of your placeholders {@link QMessage} so that it resets the parsed message for next use
     * @return the parsed message
     */
    public String get(){
        String result = parsed;
        parsed = message;
        return result;
    }

    /**
     *  Parses out a given placeholder and returns itself, so you can continue to parse other placeholders.
     *  Make sure you call {@link #get() Get} after finishing parsing to reset its placeholders.
     * @param placeholder the placeholder that will be replaced,
     *                    make sure you do not include the brackets
     * @param value the value that replaces the placeholder
     * @return itself but with an updated parsed message
     */
    public QMessage parse(String placeholder, String value){
        parsed = parsed.replaceAll("<" + placeholder + ">", value);
        return this;
    }
}
