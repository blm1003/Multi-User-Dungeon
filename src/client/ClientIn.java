package client;

import client.view.WriteToConsole;
import common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Takes information
 * from the server and
 * acts on it.
 * @author Anna Murphy
 */
public class ClientIn extends Thread
{

    private Socket server;
    private ClientMain client;

    private BufferedReader receiveFromServer;

    private WriteToConsole consoleWriter;

    /**
     * Constructor
     * Creates an input stream that
     * gets server commands.
     * @param server
     */
    public ClientIn(Socket server, ClientMain client, WriteToConsole consoleWriter)
    {
        this.server = server;
        this.client = client;
        this.consoleWriter = consoleWriter;

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
        while (client.online())
        {
            String serverCommand;
            try
            {
                serverCommand = receiveFromServer.readLine();
                this.printMessage(serverCommand);
            }
            catch (IOException ioe)
            {
                System.out.println(
                        Constants.READ_SERVER_STREAM_ERROR_MESSAGE);
            }
        }
    }

    /**
     * This is the method that is
     * changed depending on the view
     * being used.
     * @param message message being sent.
     */
    public void printMessage (String message)
    {
        //Format the message
        //Print the message

        System.out.println(message);
        consoleWriter.write(message);
    }
}
