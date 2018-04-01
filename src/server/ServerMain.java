package server;

import common.Chatroom;
import common.Constants;
import common.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Set;

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

    public User getUser (String username)
    {
        return this.users.get(username);
    }

    /**
     * Method that sends a message
     * to a specific user. Message is
     * raw text going in, and is formatted
     * in this method.
     * @param username user getting
     *                 the message.
     * @param message message being
     *                sent.
     */
    public void sendMessageToUser (String username, String message)
    {
        String newMessage = ServerMessages.
                formatSendMessageToUser(message);
        users.get(username).printMessage(newMessage);
    }

    /**
     * Method that iterates through all
     * open chatrooms and returns a string
     * of all of them.
     * @return fake list of rooms
     */
    public String getRooms ()
    {
        String roomsList = "";
        Set<String> rooms = chatrooms.keySet();
        for (String room : rooms)
        {
            roomsList += room + "\n";
        }
        return roomsList;
    }

    /**
     * Tests to see if there is
     * already a room under
     * a name.
     * @param name of the
     *             potential room
     * @return true/false
     */
    public boolean doesRoomExist (String name)
    {
        return chatrooms.containsKey(name);
    }

    /**
     * Creates a new room and
     * puts it in the collection of
     * rooms.
     * @param server The instance of
     *               ServerMain
     * @param roomName Name of the
     *                 room
     */
    public void openRoom (ServerMain server, String roomName)
    {
        this.chatrooms.put(roomName, new Chatroom(server, roomName));
    }

    /**
     * Returns a Chatroom with the name
     * of the passed in string.
     * @param room name of the room
     * @return room in question.
     */
    public Chatroom getRoom (String room)
    {
        return chatrooms.get(room);
    }

    /**
     * Removes the user from the
     * collection of online users, and
     * @param username User
     *                 being disconnected.
     * @param chatroom User is
     *                 currently connected to.
     */
    public void disconnectUser (String username, String chatroom)
    {
        users.remove(username);
        this.getRoom(chatroom).removeUser(username);
    }
}
