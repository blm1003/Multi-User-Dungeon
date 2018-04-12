package common;

import bots.Bot;
import bots.Dungeon;
import bots.Echo;
import server.ServerMain;
import server.ServerMessages;

import java.util.Hashtable;
import java.util.function.BiConsumer;

/**
 * Object for a chatroom.
 * Contains a list of the users,
 * and two booleans (one for a
 * MUD and one for a password).
 */
public class Chatroom
{
    private String name;
    private Hashtable<String, User> usersInRoom;
    private Hashtable <String, Bot> bots;
    private ServerMain server;
    private String password;
    private boolean isLocked;
    private boolean hasMUD;

    /**
     * Constructor.
     * Creates new instances of
     * variables. Nothing special
     * here.
     */
    public Chatroom (ServerMain server, String roomName)
    {
        this.name = roomName;
        this.usersInRoom = new Hashtable<>();
        this.bots = new Hashtable<>();
        this.password = "";
        this.isLocked = false;
        this.hasMUD = false;
        this.server = server;
    }

    /**
     * Locks the chatroom with a
     * password.
     * @param password locking
     *                 the chatroom
     */
    public void lock (String password)
    {
        this.password = password;
        this.isLocked = true;
    }

    /**
     * Resets the password
     * to an empty string and
     * toggles the boolean.
     */
    public void unlock ()
    {
        this.password = "";
        this.isLocked = false;
    }

    public boolean login (String password)
    {
        if (this.isLocked)
        {
            if (this.password.equals(password))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a user to the
     * collection of users in
     * the room.
     * @param user String user.
     */
    public void addUserToRoom (String user)
    {
        this.usersInRoom.put(
                user, server.getUser(user));
    }

    /**
     * Post a message in the chat to all users.
     * @param message
     */
    public void post (String message)
    {
        for (User user : usersInRoom.values())
        {
            user.printMessage(message);
        }
    }

    public void removeUser (String username)
    {
        usersInRoom.remove(username);
    }

    /**
     * Calls the method isEmpty
     * on the hashtable of users.
     * @return Boolean
     */
    public boolean isEmpty ()
    {
        return this.usersInRoom.isEmpty();
    }

    public boolean hasUser (String name)
    {
        return this.usersInRoom.containsKey(name);
    }

    /**
     * Method that starts
     * a bot in a chatroom
     * after a specific user
     * command.
     * @param botName of the
     *                bot being started.
     */
    public boolean addBot (String botName)
    {
        boolean success = true;
        Bot bot;
        switch (botName)
        {
            case ("Dungeon"):
                bot = new Dungeon(server, name);
                bot.start();
                break;
            case ("Echo"):
                bot = new Echo(server, name);
                bot.start();
                break;
            default:
                success = false;
                bot = null;
                break;
        }
        if (success)
        {
            this.bots.put(botName, bot);
        }
        return success;
    }

    /**
     * Method to  check
     * whether or not a given
     * room has a bot of a given
     * type.
     * @param botName of the bot.
     * @return true/false
     */
    public boolean hasBot (String botName)
    {
        return this.bots.containsKey(botName);
    }

    /**
     * Adds an action to the
     * bot's queue of actions.
     * @param botName bot being
     *                instructed.
     * @param instruction to be carried
     *                    out.
     */
    public void addInstruction (String botName, String instruction)
    {
        this.bots.get(botName).addAction(instruction);
    }

}
