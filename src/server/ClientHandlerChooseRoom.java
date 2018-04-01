package server;

import common.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.SQLOutput;

/**
 * Thread that has the user pick
 * the specific chatroom they want
 * to enter. Some are password
 * protected, and require the user
 * to authenticate.
 * @author Anna Murphy
 */
public class ClientHandlerChooseRoom extends ClientHandlerLogin
{
    private Socket client;
    private ServerMain server;

    private String username;

    /**
     * Streams that allow the thread to
     * read and write to/from the client.
     */

    private PrintStream clientPrinter;
    private BufferedReader clientReader;

    /**
     * Constructor.
     * Calls the super, and assigns
     * the username.
     * @param client Client
     * @param server Server Main
     * @param username User
     */
    public ClientHandlerChooseRoom (Socket client, ServerMain server, String username)
    {
        super(client, server);
        this.client = client;
        this.server = server;
        this.username = username;

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
        System.out.println("Choose Room Handler Online for " + username);
        String room;
        while (true)
        {
            try
            {
                clientPrinter.println("Which chatroom do you want to enter?");
                clientPrinter.println(server.getRooms());
                room = clientReader.readLine();
                if (server.doesRoomExist(room))
                {
                    clientPrinter.println("Entering room " + room + "...");
                    server.getRoom(room).addUserToRoom(username);
                    System.out.println("User " + username + " has entered room " + room);
                    break;
                }
                else
                {
                    clientPrinter.println(room + " is not an open room currently. Create it? [Y/N]");
                    String clientResponse = clientReader.readLine();
                    if (clientResponse.toLowerCase().substring(0, 1).equals("y"))
                    {
                        System.out.println("User " + username + " has opened room " + room);
                        System.out.println("User " + username + " has entered room " + room);
                        server.openRoom(server, room);
                        server.getRoom(room).addUserToRoom(username);
                        clientPrinter.println("Entering room " + room + "...");
                        break;
                    }
                }
            } catch (IOException ioe)
            {
                System.out.println(Constants.READ_CLIENT_STREAM_ERROR_MESSAGE);
            }
        }
        System.out.println("Choose Room Handler Offline for " + username);
        new ClientHandlerChatroom(client, server, username, room).start();
    }

}
