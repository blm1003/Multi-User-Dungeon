package server;

public class ServerMessages
{
    public static String formatSendMessageToUser (String message)
    {
        return formatSendMessageToUser(message, "Anonymous");
    }

    public static String formatSendMessageToUser (String message, String username)
    {
        return formatSendMessageToUser(message, username, "All");
    }

    public static String formatSendMessageToUser(String message,
                                                 String username,
                                                 String recipient)
    {
        return "[" + username + " >> " + recipient + "]: " + message;
    }
}
