package bots.dungeon;

/**
 * Object for storing the potential
 * options out of one room.
 * @author Anna Murphy
 */
public class Option
{
    /**
     * See constructor for descriptions
     * of these variables.
     */
    private int optionNumber;
    private String presentedText;
    private String optionText;
    private RoomTypes nextRoom;

    /**
     * Constructor.
     * Assigns variables.
     * @param num Option number for
     *            a given option.
     * @param text1 Text show to the
     *              user for a given option.
     * @param text2 Text associated
     *             with that option out.
     * @param room Type of room this
     *             room leads too.
     */
    public Option (int num,
                   String text1,
                   String text2,
                   RoomTypes room)
    {
        this.optionNumber = num;
        this.presentedText = text1;
        this.optionText = text2;
        this.nextRoom = room;
    }

    /**
     * There really isn't a need to
     * distinguish between options
     * save for the option number
     * they have.
     * This is only important because they
     * are being stored in a HashMap.
     * @return this.optionNumber
     */
    @Override
    public int hashCode()
    {
        return this.optionNumber;
    }

    /**
     * Getter for the presented
     * text. Printed to the chatroom
     * before the users choose an option.
     * @return this.presentedText
     */
    public String getPresentedText()
    {
        return presentedText;
    }

    /**
     * Getter for option text.
     * Printed to chatroom when the
     * option is selected.
     * @return this.optionText
     */
    public String getOptionText()
    {
        return optionText;
    }

    /**
     * Getter for the next room
     * type. Passed as a parameter for
     * the construction of the next
     * room.
     * @return this.nextRoom
     */
    public RoomTypes getNextRoom()
    {
        return nextRoom;
    }
}
