package gg.quartzdev.mc.lib.qlibpaper.lang;

public class QMessage
{

    private final String defaultMessage;
    private String message;
    private String parsed;

    public QMessage(String defaultMessage)
    {
        this.defaultMessage = defaultMessage;
        this.message = defaultMessage;
        this.parsed = defaultMessage;
    }

    /**
     * Gets the parsed {@link String} message and resets the parsed message back to the original message.
     * This should be called after parsing all of your placeholders {@link QMessage} so that it resets the parsed message for next use
     *
     * @return the parsed message
     */
    public String get()
    {
        String result = parsed;
        parsed = message;
        return result;
    }

    /**
     * Gets the default english message
     *
     * @return
     */
    public String getDefault()
    {
        return defaultMessage;
    }

    /**
     * Changes the serialized message inside the {@link QMessage} that supports MiniMessage formatting
     *
     * @param message the new message
     */
    public void set(String message)
    {
        this.message = message;
        this.parsed = message;
    }

    /**
     * Parses out a given placeholder and returns itself, so you can continue to parse other placeholders.
     * Make sure you call {@link #get() Get} after finishing parsing to reset its placeholders.
     *
     * @param placeholder the placeholder that will be replaced,
     *                    make sure you do not include the brackets
     * @param value       the value that replaces the placeholder
     * @return itself but with an updated parsed message
     */
    public QMessage parse(String placeholder, String value)
    {
        parsed = parsed.replaceAll("<" + placeholder + ">", value);
        return this;
    }

    /**
     * Parses out a given placeholder and returns itself, so you can continue to parse other placeholders.
     * Make sure you call {@link #get() Get} after finishing parsing to reset its placeholders.
     *
     * @param placeholder the {@link QPlaceholder} that will be replaced,
     *                    make sure you do not include the brackets
     * @param value       the value that replaces the placeholder
     * @return itself but with an updated parsed message
     */
    public QMessage parse(QPlaceholder placeholder, String value)
    {
        parsed = parsed.replaceAll(placeholder.get(), value);
        return this;
    }
}
