package bots;

import server.ServerMain;
import server.ServerMessages;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Thread that is
 * run when a user
 * in a room requests
 * that a Bot be
 * opened in a chatroom.
 *
 * The bot acts as another user in
 * the chatroom, being able to be
 * "printed to" and send messages
 * to the other users.
 *
 * @author Anna
 */
public abstract class Bot extends Thread
{

    private Queue<String> instructions;
    private boolean runWhile;
    private ServerMain server;
    private String chatroom;
    private String startupMessage;

    /**
     * Constructor
     */
    public Bot(ServerMain server, String chatroom)
    {
        this.instructions = new PriorityQueue<>();
        this.runWhile = true;
        this.server = server;
        this.chatroom = chatroom;
        this.startupMessage = this + " has started!";
    }

    /**
     * Instead of having a
     * variable hold the bot
     * name, each bot in a room
     * will be identified with the
     * type of bot it is.
     * @return Name of the Bot
     */
    @Override
    public String toString ()
    {
        return "Bot";
    }

    /**
     * Bot Loop.
     * Dequeues instructions,
     * and acts on them. The
     * method
     */
    public void run ()
    {
        System.out.println("Bot " + this + " started running.");
        postToRoom(startupMessage);
        while (runWhile)
        {
            String i = getAction();
            if (i != null)
            {
                this.execute(i);
            }
        }
    }

    /**
     * Adds an action to the
     * queue of actions the bot needs to
     * complete.
     * @param action String containing
     *               the action.
     */
    public synchronized void addAction (String action)
    {
        System.out.println(this + " received a new instruction.");
        this.instructions.add(action);
    }

    /**
     * Allows for the the queue to be
     * polled from in a synchronized manner.
     * @return Instruction.
     */
    public synchronized String getAction ()
    {
        return instructions.poll();
    }

    /**
     * Allows a bot to post a message to
     * the chatroom of which it is
     * a part.
     * @param message being sent.
     */
    public void postToRoom (String message)
    {
        this.server.getRoom(chatroom).post(
                ServerMessages.formatSendMessageToUser(
                        message, toString()));
    }

    /**
     * The execution of each individual
     * instruction must be held within
     * each variation of the bot.
     * @param instruction being executed.
     */
    public abstract void execute (String instruction);

    /**
     * End the bot operation.
     */
    public void endBot ()
    {
        this.runWhile = false;
    }
}
