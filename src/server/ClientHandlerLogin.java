package server;

import common.Constants;

import java.io.*;
import java.net.Socket;

/**
 * Thread that logs the user on to the
 * the network. Not super important as it
 * just asks for the user's name, and no
 * password. Chatrooms will be specifically
 * locked by password.
 * @author Anna Murphy
 */

public class ClientHandlerLogin extends Thread
{

    private Socket client;
    private ServerMain server;

    /**
     * Streams that allow the thread to
     * read and write to/from the client.
     */

    private PrintStream clientPrinter;
    private BufferedReader clientReader;

    /**
     * Constructor for the login thread.
     * Creates a server that is assesible to the
     * client, and a client to print to.
     * @param client connect user
     * @param server host
     */
    public ClientHandlerLogin (Socket client, ServerMain server)
    {
        this.client = client;
        this.server = server;

        // Initialize the Streams
        try
        {
            //Once the User Object is created, the threads will not have to
            //have the printer information. This is only used in the login class.
            this.clientPrinter = new PrintStream(client.getOutputStream());

            this.clientReader = new BufferedReader(new
                    InputStreamReader(client.getInputStream()));
        }
        catch (IOException ioe)
        {
            System.out.println(Constants.IN_OUT_TO_CLIENT_INIT_ERROR_MESSAGE);
            System.exit(Constants.IN_OUT_TO_CLIENT_INIT_EXIT_CODE);
        }
    }

    public void run ()
    {
        System.out.println("Login Handler Online.");
        clientPrinter.println("Welcome!");
        String username;
        while (true)
        {
            clientPrinter.println("Enter A username:");
            String tempName;
            try
            {
                tempName = clientReader.readLine();
            }
            catch (IOException ioe)
            {
                System.out.println(
                        Constants.READ_CLIENT_STREAM_ERROR_MESSAGE);
                tempName = "";
            }

            if (!server.isUsernameTaken(tempName))
            {
                clientPrinter.println("Your username is now set as " + tempName + ".");
                username = tempName;
                server.addUser(username, client);
                break;
            }
            else
            {
                clientPrinter.println("This username is already taken," +
                        " or is invalid.");
            }
        }
        System.out.println("Login Handler Offline");
        new ClientHandlerChooseRoom(client, server, username).start();
    }
}
