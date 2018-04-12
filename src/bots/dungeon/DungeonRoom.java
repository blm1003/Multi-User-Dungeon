package bots.dungeon;

import bots.Dungeon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * Abstract class that represents a
 * specific room in a dungeon.
 * This will take in an enum (RoomTypes),
 * generate a room based on a randomly
 * chosen file of that type, and wait for
 * user response to progress through to
 * the next room.
 * @author Anna Murphy
 */
public abstract class DungeonRoom
{

    /**
     * General Information about
     * the room currently occupied by
     * the users.
     */
    protected String roomName;
    protected String openingText;
    protected boolean terminus;

    /**
     * Stores the options given to the user.
     */
    protected HashMap <Integer, Option> options;

    /**
     * Dungeon to which this room is  a part.
     */
    protected Dungeon dungeon;

    /**
     * Map used to prevent infinite loops in
     * choosing rooms. If there are no rooms of
     * a type left unvisited, it defaults to a boss.
     */
    protected HashMap<RoomTypes, Integer> visitCount;

    /**
     * Constructor.
     * Reads from a file of the type
     * specified, and assigns class variables
     * based on the content of the file.
     * @param roomType enum containing
     *                 the type of the room
     *                 in the dungeon.
     */
    public DungeonRoom (RoomTypes roomType, Dungeon dungeon)
    {
        this.dungeon = dungeon;
        this.options = new HashMap<>();
        this.visitCount = new HashMap<>();
        this.visitCount.put(RoomTypes.Hallway, 0);
        this.visitCount.put(RoomTypes.Entrance, 0);
        this.visitCount.put(RoomTypes.TrapRoom, 0);
        this.visitCount.put(RoomTypes.PuzzleRoom, 0);
        this.visitCount.put(RoomTypes.TotalPartyKill, 0);
        this.visitCount.put(RoomTypes.BossRoom, 0);
        this.visitCount.put(RoomTypes.TreasureRoom, 0);

        //Choose What File To Use
        String dirToFolder;
        switch (roomType)
        {
            case Hallway:
                dirToFolder = "Hallway";
                break;
            case TrapRoom:
                dirToFolder = "Trap";
                break;
            case PuzzleRoom:
                dirToFolder = "Puzzle";
                break;
            case BossRoom:
                dirToFolder = "Boss";
                break;
            case TreasureRoom:
                dirToFolder = "Treasure";
                break;
            case TotalPartyKill:
                dirToFolder = "TotalPartyKill";
                break;
            default:
                dirToFolder = "Hallway";
                break;
        }
        String filePath = new File("").getAbsolutePath();
        filePath = filePath + "\\roomFiles\\" + dirToFolder;
        //System.out.println("File " + filePath);
        File dir = new File (filePath);
        File[] files = dir.listFiles();
        Random rand = new Random();
        File file = files[rand.nextInt(files.length)];

        if (visitCount.get(roomType) == files.length)
        {
            /**
             * Assuming you have visited all of the same type
             * of room, let's take you to a special boss.
             */
            filePath = new File("").getAbsolutePath() +
                    "\\roomFiles\\PreventTheLoop";
            file = new File(filePath);
        }
        else
        {
            /**
             * Re choose rooms randomly until you
             * get one that you haven't seen before.
             */
            while (true)
            {
                if (this.dungeon.hasVisted(file.getAbsolutePath()))
                {
                    file = files[rand.nextInt(files.length)];
                }
                else
                {
                    this.dungeon.addVisted(file.getAbsolutePath());
                    int count = this.visitCount.get(roomType) + 1;
                    this.visitCount.replace(roomType, count);
                    break;
                }
            }
        }

        //Assign Variables Based On File Contents
        this.terminus = false;
        try
        {
            BufferedReader fileIn = new BufferedReader(new FileReader(file));
            this.roomName = fileIn.readLine();
            this.openingText = fileIn.readLine();
            int numOptions = 1;
            while (true)
            {
                String lineContent = fileIn.readLine();
                if (lineContent == null)
                {
                    break;
                }
                String[] lineSplit = lineContent.split("::");
                //System.out.println("Line Content " + lineContent);
                String userText = lineSplit[0];
                String roomText = lineSplit[1];
                RoomTypes types;
                switch (lineSplit[2])
                {
                    case "Hallway":
                        types = RoomTypes.Hallway;
                        break;
                    case "Puzzle":
                        types = RoomTypes.PuzzleRoom;
                        break;
                    case "Trap":
                        types = RoomTypes.TrapRoom;
                        break;
                    case "Boss":
                        types = RoomTypes.BossRoom;
                        break;
                    case "Treasure":
                        types = RoomTypes.TreasureRoom;
                        break;
                    case "TotalPartyKill":
                        types = RoomTypes.TotalPartyKill;
                        break;
                    default:
                        types = RoomTypes.Hallway;
                        break;
                }
                this.options.put(numOptions,
                        new Option(
                                numOptions,
                                userText,
                                roomText,
                                types));
                numOptions++;
            }
        }
        catch (IOException ioe)
        {
            System.out.println("Error in creating a new Dungeon Room.");
        }
    }

    /**
     * Returns the string that is the
     * opening text for a room.
     * @return this.openingText
     */
    public String getOpeningText ()
    {
        return this.openingText;
    }

    /**
     * Get the text of a specific option.
     * This will be called after the option
     * has been chosen, but before the new
     * room has been created.
     * @param optionNum number
     *                  of the option being accessed.
     * @return Outro text for this room leading
     * into the next one.
     */
    public String getOptionText (int optionNum)
    {
        return options.get(optionNum).getOptionText();
    }

    /**
     * Get the type of room to be
     * generated by a specific option.
     * To be called after the outro text
     * has been sent.
     * @param optionNum number
     *                  of the option being accessed.
     * @return RoomType of the next room.
     */
    public RoomTypes getNextRoomType (int optionNum)
    {
        return options.get(optionNum).getNextRoom();
    }

    /**
     * Method to get all the text that will be
     * sent to the chatroom for a given set of
     * options.
     * @return collected presentedText variables
     * for all the option objects in options.
     */
    public String getOptions ()
    {
        String optionsText = "";
        for (Option option : options.values())
        {
            optionsText = optionsText + "\n" + option.getPresentedText();
        }
        return optionsText;
    }

    /**
     * Returns whether or not this
     * room will be the end of the chain.
     * @return
     */
    public boolean isTerminus()
    {
        return terminus;
    }
}
