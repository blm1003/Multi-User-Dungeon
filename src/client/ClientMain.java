package client;

import common.Constants;

import java.io.IOException;
import java.net.Socket;

public class ClientMain
{
    public static void main (String[] args)
    {
        if (args.length != 0)
        {
            System.out.println(
                    Constants.INCORRECT_CLIENT_USAGE_ERROR_MESSAGE);
            System.exit(Constants.INCORRECT_CLIENT_USAGE_EXIT_CODE);
        }
        try
        {
            Socket clientSocket = new Socket(
                    "localhost", Constants.SERVER_PORT);
            new ClientPrinter(clientSocket).start();
            new ClientReceiver(clientSocket).start();
        }
        catch (IOException ioe)
        {

        }
    }
}