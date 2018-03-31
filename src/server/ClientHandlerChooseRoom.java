package server;

import common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Thread that has the user pick
 * the specific chatroom they want
 * to enter. Some are password
 * protected, and require the user
 * to authenticate.
 * @author Anna Murphy
 */
public class ClientHandlerChooseRoom extends Thread
{

    private String username;
    private Socket client;
    private ServerMain server;

    /**
     * Input stream from the client.
     * Needed so this stream can get
     * information from the client.
     */
    private BufferedReader clientReader;

    /**
     * Constructor.
     * Gets the user, creates the input stream,
     * and stores the server information.
     * @param username Username
     * @param client Socket object
     * @param server server main
     */
    public ClientHandlerChooseRoom (String username, Socket client, ServerMain server)
    {
        this.username = username;
        this.client = client;
        this.server = server;

        //Create the input stream
        try
        {
            clientReader = new BufferedReader(
                    new InputStreamReader(this.client.getInputStream()));
        }
        catch (IOException ioe)
        {
            System.out.println(Constants.IN_OUT_TO_CLIENT_INIT_ERROR_MESSAGE);
        }
    }

    public void run ()
    {
        System.out.println("Choose Room Thread for user " + username +
                " is online. Choosing Room.");
        server.sendMessageToUser(username, "Choose your chatroom.");
    }
}
