package bots;

import server.ServerMain;

/**
 * Specific bot extension
 * that creates a dungeon
 * the uses in the room can
 * interact with.
 *
 * @author Anna Murphy
 */
public class Dungeon extends Bot
{

    /**
     * Constructor
     */
    public Dungeon (ServerMain server, String chatroom)
    {
        super(server, chatroom);
    }

    /**
     * The Name of this bot
     * is "Echo Bot"
     * @return "Echo Bot"
     */
    @Override
    public String toString()
    {
        return "Dungeon Bot";
    }

    /**
     * This is an echo bot.
     * The instruction, whatever it is, is
     * just echoed back at the user.
     * @param instruction being executed.
     */
    @Override
    public void execute(String instruction)
    {
        postToRoom(instruction);
    }
}
