package server;

import common.Chatroom;
import common.Constants;
import common.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Hashtable;

public class ServerMain
{

    private Hashtable<String, Chatroom> chatrooms;
    private Hashtable<String, User> users;

    public ServerMain ()
    {
        this.chatrooms = new Hashtable<>();
        this.users = new Hashtable<>();
    }

    public void startServer ()
    {
        ServerSocket server;
        try
        {
            server = new ServerSocket(Constants.SERVER_PORT);
        }
        catch (IOException ioe)
        {
            // Necessary because server is not initialized otherwise -_-
            server = null;

            System.out.println(Constants.SERVER_STARTUP_ERROR_MESSAGE);
            System.exit(Constants.SERVER_STARTUP_EXIT_CODE);
        }
        while (true)
        {
            try
            {
                Socket client = server.accept();
                System.out.println("New user on port " + client.getPort() + ".");
                new ClientHandlerLogin(client, this).start();
            }
            catch (IOException ioe)
            {
                System.out.println(Constants.SERVER_ACCEPT_ERROR_MESSAGE);
            }
        }
    }

    /**
     * Main. Creates an instance of the
     * server and starts it.
     * @param args program arguments.
     */
    public static void main (String[] args)
    {
        ServerMain serverMain = new ServerMain();
        serverMain.startServer();
    }

    /**
     * Simple boolean that check whether or
     * not a given username is taken by another
     * user already online.
     * @param tryName name being tried.
     * @return true/false.
     */
    public boolean isUsernameTaken (String tryName)
    {
        return users.containsKey(tryName);
    }

    /**
     * Add a new connected user to the
     * collection of users online.
     * @param username Username
     * @param client Socket information
     */
    public void addUser (String username, Socket client)
    {
        System.out.println("New user named " + username +
                " on port " + client.getPort() + ".");
        users.put(username, new User(username, client));
    }

    public void sendMessageToUser (String username, String message)
    {
        String newMessage = ServerMessages.
                formatSendMessageToUser(message);
        users.get(username).printMessage(newMessage);
    }

}
