package gg.quartzdev.lib.qlibpaper.commands;

@SuppressWarnings("unused")
public class CommandExistsException extends Exception
{
    private CommandExistsException()
    {
        super();
    }


    public CommandExistsException(String message)
    {
        super(message);
    }

    public CommandExistsException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
