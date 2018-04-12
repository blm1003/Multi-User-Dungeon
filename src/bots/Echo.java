package bots;

import server.ServerMain;

/**
 * Test bot. This implements the function of
 * an echo server, printing back every
 * command it gets. This serves no
 * purpose other than as a test.
 * @author Anna Murphy
 */
public class Echo extends Bot
{

    /**
     * Constructor
     */
    public Echo (ServerMain server, String chatroom)
    {
        super(server, chatroom);
        startupMessage = this + " has started! " +
                "Any command you send will be repeated! " +
                "Send the command 'stop' end this process.";
    }

    /**
     * The Name of this bot
     * is "Echo Bot"
     * @return "Echo Bot"
     */
    @Override
    public String toString()
    {
        return "Echo Bot";
    }

    /**
     * This is an echo bot.
     * The instruction, whatever it is, is
     * just echoed back at the user.
     */
    @Override
    public void execute()
    {
        String instruction = getAction();
        if (instruction != null)
        {
            System.out.println(this + " echoing string: " + instruction);
            if (instruction.equals("stop"))
            {
                this.endBot();
            }
            else
            {
                postToRoom(instruction);
            }
        }
    }
}
