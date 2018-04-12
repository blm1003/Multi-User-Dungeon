package bots;

import bots.dungeon.*;
import server.ServerMain;

import java.util.HashSet;

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

    private DungeonRoom room;
    private RoomTypes nextType;
    private HashSet<String> vistedRooms;

    /**
     * Constructor
     */
    public Dungeon (ServerMain server, String chatroom)
    {
        super(server, chatroom);
        this.startupMessage = "You and your party enter a dungeon. " +
                "Who knows what horrors await...";
        this.endMessage = "Congratulations!";
        this.nextType = RoomTypes.Entrance;
        this.vistedRooms = new HashSet<>();
    }

    /**
     * Called from the terminus.
     * The message changes to either be happy
     * with treasure or sad with TPK.
     * @param message new endmessage content.
     */
    public void setEndMessage (String message)
    {
        this.endMessage = message;
    }

    /**
     * The Name of this bot
     * is "Dungeon Bot"
     * @return "Dungeon Bot"
     */
    @Override
    public String toString()
    {
        return "Dungeon Bot";
    }

    /**
     * This is in a while true loop.
     * Post the the room what the situation is,
     * wait for a responce, and then
     * enter a new room.
     * Repeat.
     */
    @Override
    public void execute()
    {
        switch (nextType)
        {
            case Entrance:
                room = new Entrance(this);
                break;
            case Hallway:
                room = new Hallway(this);
                break;
            case PuzzleRoom:
                room = new PuzzleRoom(this);
                break;
            case TrapRoom:
                room = new TrapRoom(this);
                break;
            case BossRoom:
                room = new BossRoom(this);
                break;
            case TreasureRoom:
                room = new Treausre(this);
                break;
            case TotalPartyKill:
                room = new TotalPartyKill(this);
                break;
            default:
                room = new Hallway(this);
                break;
        }
        postToRoom(room.getOpeningText());
        if (room.isTerminus())
        {
            this.endBot();
        }
        else
        {
            postToRoom(room.getOptions());
            while (true)
            {
                String instruction = getAction();
                if (instruction != null)
                {
                    try
                    {
                        instruction = instruction.substring(1);
                        int choice =  Integer.parseInt(instruction);
                        String postText = room.getOptionText(choice);
                        if (postText != null)
                        {
                            postToRoom(postText);
                            this.nextType = room.getNextRoomType(choice);
                            break;
                        }
                        else
                        {
                            postToRoom("Choice Not Found. Choose again.");
                        }
                    }
                    catch (NumberFormatException nfe)
                    {
                        postToRoom("Invalid Choice. Choose again.");
                    }
                }
            }
        }
    }

    /**
     * Method to check if the room being tried has
     * already been visited in this particular session.
     * @param roomPath File path to the room in question.
     * @return true or false if it is in the set.
     */
    public boolean hasVisted (String roomPath)
    {
        return this.vistedRooms.contains(roomPath);
    }

    /**
     * Once a room has been visited,
     * add it to the set.
     * @param roomPath File path to the room in
     *                 question.
     */
    public void addVisted (String roomPath)
    {
        this.vistedRooms.add(roomPath);
    }
}
