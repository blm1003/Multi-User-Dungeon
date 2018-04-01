package client;

import common.Constants;

public class ClientMessages
{
    public static String formatSendMessage (String message, ClientMain clientInfo)
    {
        return "message" +
                Constants.SEPARATOR + clientInfo.getUsername() +
                Constants.SEPARATOR + message;
    }
}
