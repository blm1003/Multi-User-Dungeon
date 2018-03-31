package client;

import common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientReceiver extends Thread
{

    private Socket server;

    private BufferedReader receiveFromServer;

    /**
     * Constructor
     * Creates an input stream that
     * gets server commands.
     * @param server
     */
    public ClientReceiver(Socket server)
    {
        this.server = server;
        //Create Print Stream
        try
        {
            receiveFromServer = new BufferedReader(
                    new InputStreamReader(server.getInputStream()));
        }
        catch (IOException ioe)
        {
            System.out.println(
                    Constants.IN_OUT_TO_CLIENT_INIT_ERROR_MESSAGE);
            System.exit(Constants.IN_OUT_TO_CLIENT_INIT_EXIT_CODE);
        }
    }

    /**
     * Overridden Thread method.
     * This will receive, parse, and act
     * on server instructions.
     */
    public void run ()
    {
        while (true)
        {

        }
    }

    /**
     * This is the method that is
     * changed depending on the view
     * being used.
     * @param message message being sent.
     */
    public void sendMessage (String message)
    {

    }

}
