package client;

import common.Constants;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;

public class ClientPrinter extends Thread
{

    private Socket server;

    private PrintStream printToServer;

    /**
     * Constructor
     * Creates the input stream
     * from the socket.
     * @param server Object created on
     *               client startup.
     */
    public ClientPrinter (Socket server)
    {
        this.server = server;

        //Create Print Stream
        try
        {
            printToServer = new PrintStream(server.getOutputStream());
        }
        catch (IOException ioe)
        {
            System.out.println(
                    Constants.IN_OUT_TO_CLIENT_INIT_ERROR_MESSAGE);
            System.exit(Constants.IN_OUT_TO_CLIENT_INIT_EXIT_CODE);
        }
    }

    /**
     * Overridden thread method.
     * This will take user input (from
     * any source) and act on the
     * user command.
     */
    public void run ()
    {
        while (true)
        {

        }
    }
}
