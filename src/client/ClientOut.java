package client;

import common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Class that gets data from
 * the user, and sends it to the
 * server.
 * @author Anna Murphy
 */
public class ClientOut extends Thread
{

    private Socket server;
    private ClientMain client;

    private PrintStream printToServer;
    private BufferedReader clientIn;


    /**
     * Constructor
     * Creates the input stream
     * from the socket.
     * @param server Object created on
     *               client startup.
     */
    public ClientOut(Socket server, ClientMain client)
    {
        this.server = server;
        this.client = client;

        //Create Stream/Reader
        try
        {
            //TODO change clientIn to accept information from javaFX
            clientIn = new BufferedReader(
                    new InputStreamReader(System.in));
            printToServer = new PrintStream(server.getOutputStream());
        }
        catch (IOException ioe)
        {
            System.out.println(
                    Constants.IN_OUT_TO_CLIENT_INIT_ERROR_MESSAGE);
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
        String clientInput;
        while (client.online())
        {
            clientInput = client.getUserIn();
            if (clientInput != null && !clientInput.equals("") && !clientInput.equals("\n"))
            {
                printToServer.println(clientInput);
                if (clientInput.equals("exit"))
                {
                    client.disconnect();
                    System.exit(0);
                }
            }
        }
    }

    /**
     * clientIn needs to be changed from a stream
     * to a queue. The textArea in UserInterface.java
     * needs to be given an event handler to make it so
     * that whenever enter is pressed, it adds a string to
     * the queue.
     *
     * This queue will probably be in ClientMain, allowing access
     * to it by objects in both this class and ClientMain.
     */

}
