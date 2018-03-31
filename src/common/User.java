package common;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Object that holds user data.
 * Allows for accessible information,
 * ability to get a username, and to
 * print to a specific user's print stream.
 */
public class User
{
    /**
     * Information about the client.
     */
    private String username;
    private Socket client;

    /**
     * Print stream for a specific client.
     * Allows for any thread (server side)
     * to send messages to a specific user.
     */
    private PrintStream printToUser;

    public User (String username, Socket client)
    {
        this.username = username;
        this.client = client;

        //Create PrintStream
        try
        {
            this.printToUser = new PrintStream(this.client.getOutputStream());
        }
        catch (IOException ioe)
        {
            System.out.println(Constants.IN_OUT_TO_CLIENT_INIT_ERROR_MESSAGE);
        }
    }

    /**
     * Getter for the username.
     * @return this.username
     */
    public String getUsername ()
    {
        return this.username;
    }

    /**
     * Public method that prints a message
     * to this user.
     * @param message Text string formatted
     *                for the user parser.
     */
    public void printMessage (String message)
    {
        this.printToUser.println(message);
    }

}
